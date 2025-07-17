package com.medilabo.ms_note.controller;

import com.medilabo.ms_note.dto.NoteCreateDto;
import com.medilabo.ms_note.entity.Note;
import com.medilabo.ms_note.exception.PatientNotFoundException;
import com.medilabo.ms_note.service.INoteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for note-related operations.
 */
@RestController
@Slf4j
@RequestMapping(path = "/notes")
public class NoteController {

    private final INoteService noteService;

    /**
     * Constructor for NoteController.
     *
     * @param noteService the repository to handle note operations
     */
    public NoteController(INoteService noteService) {
        this.noteService = noteService;
        log.info("====> NoteController initialized <====");
    }

    /**
     * Retrieves a list of notes by patient ID order by creation date.
     *
     * @param patientId the ID of the patient
     * @return a list of NoteDto
     */
    @GetMapping(path = "/patient/{patientId}", headers = "X-API-VERSION=1")
    public List<Note> getNotesByPatientIdOrderByCreatedAt(@PathVariable Integer patientId) {
        log.info("====> GET /notes/patient/{} <====", patientId);
        return noteService.findByPatientIdOrderByCreatedAt(patientId);
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id the ID of the note to delete
     */
    @DeleteMapping(path = "/{id}", headers = "X-API-VERSION=1")
    public void deleteNoteById(@PathVariable String id) {
        log.info("====> DELETE /notes/{} <====", id);
        noteService.deleteById(id);
    }

    /**
     * Creates a new note.
     *
     * @param noteCreateDto the note to create
     * @return the created note
     */
    @PostMapping(headers = "X-API-VERSION=1")
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody @Valid NoteCreateDto noteCreateDto) throws PatientNotFoundException {
        log.info("====> POST /notes <====");
        return noteService.create(noteCreateDto);
    }

}
