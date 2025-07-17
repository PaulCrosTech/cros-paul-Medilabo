package com.medilabo.ms_note.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.ms_note.controller.NoteController;
import com.medilabo.ms_note.dto.NoteCreateDto;
import com.medilabo.ms_note.entity.Note;
import com.medilabo.ms_note.exception.NoteNotFoundException;
import com.medilabo.ms_note.exception.PatientNotFoundException;
import com.medilabo.ms_note.service.impl.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for NoteController.
 */
@WebMvcTest(controllers = NoteController.class)
public class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    /**
     * Test method: getNotesByPatientIdOrderByCreatedAt
     * Given: empty list of notes for a patient with ID 1
     * When: GET /notes/patient/{patientId}
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenExistingPatientWithEmptyList_whenGetNotesByPatientIdOrderByCreatedAt_thenReturnOk() throws Exception {
        // Given
        int patientId = 1;
        when(noteService.findByPatientIdOrderByCreatedAt(patientId)).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/notes/patient/{patientId}", patientId)
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isOk());
    }

    /**
     * Test method: getNotesByPatientIdOrderByCreatedAt
     * Given: a non-existing patient ID
     * When: GET /notes/patient/{patientId}
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingPatient_whenGetNotesByPatientIdOrderByCreatedAt_thenReturnNotFound() throws Exception {
        // Given
        Integer patientId = 99;
        doThrow(new PatientNotFoundException(patientId.toString())).when(noteService).findByPatientIdOrderByCreatedAt(patientId);

        // When & Then
        mockMvc.perform(get("/notes/patient/{patientId}", patientId)
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test method: deleteNoteById
     * Given: an existing note with ID 1
     * When: DELETE /notes/{id}
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenExistingNote_whenDeleteNoteById_thenReturnOk() throws Exception {
        // Given
        String noteId = "1";

        // When & Then
        mockMvc.perform(delete("/notes/{id}", noteId)
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isOk());
    }

    /**
     * Test method: deleteNoteById
     * Given: a non-existing note with ID
     * When: DELETE /notes/{id}
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingNote_whenDeleteNoteById_thenReturnNotFound() throws Exception {
        // Given
        String noteId = "non-existing-id";
        doThrow(new NoteNotFoundException(noteId)).when(noteService).deleteById(noteId);

        // When & Then
        mockMvc.perform(delete("/notes/{id}", noteId)
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test method: createNote
     * Given: a valid NoteCreateDto with patient ID and note content
     * When: POST /notes
     * Then: Return status Created (201)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenValidNote_whenCreateNote_thenReturnCreated() throws Exception {
        // Given
        String noteContent = "Note content";
        NoteCreateDto noteCreateDto = new NoteCreateDto();
        noteCreateDto.setPatientId(1);
        noteCreateDto.setNote(noteContent);
        ObjectMapper objectMapper = new ObjectMapper();
        String noteCreateDtoJson = objectMapper.writeValueAsString(noteCreateDto);

        Note createdNote = new Note();
        createdNote.setId("1");
        createdNote.setPatientId(1);
        createdNote.setNote(noteContent);

        when(noteService.create(noteCreateDto)).thenReturn(createdNote);

        // When & Then
        mockMvc.perform(post("/notes")
                        .header("X-API-VERSION", "1")
                        .contentType("application/json")
                        .content(noteCreateDtoJson))
                .andExpect(status().isCreated());
    }

    /**
     * Test method: createNote
     * Given: a NoteCreateDto with an empty note content
     * When: POST /notes
     * Then: Return status Bad Request (400)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonValidNote_whenCreateNote_thenReturnBadRequest() throws Exception {
        // Given
        NoteCreateDto noteCreateDto = new NoteCreateDto();
        noteCreateDto.setPatientId(1);
        noteCreateDto.setNote("");
        ObjectMapper objectMapper = new ObjectMapper();
        String noteCreateDtoJson = objectMapper.writeValueAsString(noteCreateDto);

        // When & Then
        mockMvc.perform(post("/notes")
                        .header("X-API-VERSION", "1")
                        .contentType("application/json")
                        .content(noteCreateDtoJson))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test method: createNote
     * Given: a NoteCreateDto with a non-existing patient ID
     * When: POST /notes
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingPatientId_whenCreateNote_thenReturnNotFound() throws Exception {
        // Given
        NoteCreateDto noteCreateDto = new NoteCreateDto();
        noteCreateDto.setPatientId(999);
        noteCreateDto.setNote("Note content");
        ObjectMapper objectMapper = new ObjectMapper();
        String noteCreateDtoJson = objectMapper.writeValueAsString(noteCreateDto);

        doThrow(new PatientNotFoundException(noteCreateDto.getPatientId().toString())).when(noteService).create(noteCreateDto);

        // When & Then
        mockMvc.perform(post("/notes")
                        .header("X-API-VERSION", "1")
                        .contentType("application/json")
                        .content(noteCreateDtoJson))
                .andExpect(status().isNotFound());
    }

    /**
     * Test method: updateNote
     * Given: a valid note ID and updated note content
     * When: PUT /notes/{id}
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenValidNote_whenUpdateNote_thenReturnOk() throws Exception {
        // Given
        String noteId = "1";
        String updatedNoteContent = "Updated note content";

        Note updatedNote = new Note();
        updatedNote.setId(noteId);
        updatedNote.setNote(updatedNoteContent);

        when(noteService.update(noteId, updatedNoteContent)).thenReturn(updatedNote);

        // When & Then
        mockMvc.perform(put("/notes/{id}", noteId)
                        .header("X-API-VERSION", "1")
                        .contentType("application/json")
                        .content(updatedNoteContent))
                .andExpect(status().isOk());
    }

    /**
     * Test method: updateNote
     * Given: a non-existing note ID
     * When: PUT /notes/{id}
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingNote_whenUpdateNote_thenReturnNotFound() throws Exception {
        // Given
        String noteId = "non-existing-id";
        String updatedNoteContent = "Updated note content";

        Note updatedNote = new Note();
        updatedNote.setId(noteId);
        updatedNote.setNote(updatedNoteContent);

        doThrow(new NoteNotFoundException(noteId)).when(noteService).update(noteId, updatedNoteContent);

        // When & Then
        mockMvc.perform(put("/notes/{id}", noteId)
                        .header("X-API-VERSION", "1")
                        .contentType("application/json")
                        .content(updatedNoteContent))
                .andExpect(status().isNotFound());
    }

}
