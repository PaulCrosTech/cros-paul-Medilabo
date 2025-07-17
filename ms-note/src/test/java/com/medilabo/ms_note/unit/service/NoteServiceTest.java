package com.medilabo.ms_note.unit.service;

import com.medilabo.ms_note.dto.NoteCreateDto;
import com.medilabo.ms_note.entity.Note;
import com.medilabo.ms_note.exception.NoteNotFoundException;
import com.medilabo.ms_note.exception.PatientNotFoundException;
import com.medilabo.ms_note.mapper.NoteMapper;
import com.medilabo.ms_note.proxies.MsPatientProxy;
import com.medilabo.ms_note.repository.NoteRepository;
import com.medilabo.ms_note.service.impl.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the NoteService class.
 */
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private NoteMapper noteMapper;
    @Mock
    private MsPatientProxy msPatientProxy;

    private List<Note> notes;

    @BeforeEach
    public void setupPerTest() {
        noteService = new NoteService(noteRepository, noteMapper, msPatientProxy);

        notes = List.of(
                new Note("NOTE-ID-1", 1, "Dupond", "Note Content", null),
                new Note("NOTE-ID-2", 1, "Dupond", "Note Content", null),
                new Note("NOTE-ID-3", 2, "Doe", "Note Content", null)
        );
    }

    /**
     * Test method: findByPatientIdOrderByCreatedAt
     * Given: list of notes
     * When: findByPatientIdOrderByCreatedAt
     * Then: Return list of notes for the specified patient ID
     */
    @Test
    public void givenPatientId_whenFindByPatientIdOrderByCreatedAt_thenReturnList() {
        // Given
        Integer patientId = 1;
        List<Note> expectedNotes = notes.stream().filter(note -> note.getPatientId().equals(1)).collect(Collectors.toList());
        when(noteRepository.findByPatientIdOrderByCreatedAtDesc(patientId)).thenReturn(expectedNotes);

        // When
        List<Note> result = noteService.findByPatientIdOrderByCreatedAt(patientId);

        // Then
        assertEquals(2, result.size());
    }


    /**
     * Test method: deleteById
     * Given: an existing note id
     * When: deleteById
     * Then: no exception is thrown
     */
    @Test
    public void giveExistingId_whenDeleteById_thenNoExceptionIsThrown() {
        // Given
        String id = "NOTE-ID-1";
        when(noteRepository.findById(id)).thenReturn(notes.stream().filter(note -> note.getId().equals(id)).findFirst());

        // When & Then
        assertDoesNotThrow(() -> noteService.deleteById(id));
    }

    /**
     * Test method: deleteById
     * Given: a non-existing note id
     * When: deleteById
     * Then: throw NoteNotFoundException
     */
    @Test
    public void giveNonExistingId_whenDeleteById_thenThrowNoteNotFoundException() {
        // Given
        String id = "NON-EXISTING-ID";
        when(noteRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoteNotFoundException.class, () -> noteService.deleteById(id));

    }


    /**
     * Test method: create
     * Given: a NoteCreateDto with a non-existing patient ID
     * When: create
     * Then: throw PatientNotFoundException
     */
    @Test
    public void givenNonExistingPatientId_whenCreate_thenThrowPatientNotFoundException() {
        // Given
        NoteCreateDto noteCreateDto = new NoteCreateDto();
        noteCreateDto.setPatientId(999);
        noteCreateDto.setNote("Note Content");

        doThrow(PatientNotFoundException.class)
                .when(msPatientProxy).getPatientLastNameById(noteCreateDto.getPatientId());

        // When & Then
        assertThrows(PatientNotFoundException.class, () -> noteService.create(noteCreateDto));
    }

    /**
     * Test method: create
     * Given: a NoteCreateDto with an existing patient ID
     * When: create
     * Then: return the created Note
     */
    @Test
    public void givenNoteCreateDto_whenCreate_thenReturnNote() {
        // Given
        Note note = notes.getFirst();
        NoteCreateDto noteCreateDto = new NoteCreateDto();
        noteCreateDto.setPatientId(note.getPatientId());
        noteCreateDto.setNote(note.getNote());

        when(msPatientProxy.getPatientLastNameById(note.getPatientId())).thenReturn(note.getLastName());
        when(noteMapper.NoteCreateDtoToNote(noteCreateDto)).thenReturn(note);
        when(noteRepository.save(any())).thenReturn(note);

        // When
        Note createdNote = noteService.create(noteCreateDto);

        // Then
        verify(msPatientProxy, times(1)).getPatientLastNameById(note.getPatientId());
        verify(noteMapper, times(1)).NoteCreateDtoToNote(noteCreateDto);
        verify(noteRepository, times(1)).save(any());
    }

    /**
     * Test method: update
     * Given: an existing note ID and new note content
     * When: update
     * Then: return the updated Note
     */
    @Test
    public void givenExistingId_whenUpdate_thenReturnUpdatedNote() {
        // Given
        Note note = notes.getFirst();
        String noteContent = "Update Note Content";

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);

        // When
        Note updatedNote = noteService.update(note.getId(), noteContent);

        // Then
        assertEquals(noteContent, updatedNote.getNote());
    }

    /**
     * Test method: update
     * Given: a non-existing note ID
     * When: update
     * Then: throw NoteNotFoundException
     */
    @Test
    public void givenNonExistingId_whenUpdate_thenThrowNoteNotFoundException() {
        //Given
        String nonExistingId = "NON-EXISTING-ID";
        when(noteRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoteNotFoundException.class, () -> noteService.update(nonExistingId, "Some Note Content"));
    }
}
