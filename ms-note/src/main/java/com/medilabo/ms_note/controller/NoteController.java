package com.medilabo.ms_note.controller;

import com.medilabo.ms_note.dto.NoteDto;
import com.medilabo.ms_note.service.INoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Retrieves a list of notes by patient ID.
     *
     * @param patientId the ID of the patient
     * @return a list of NoteDto
     */
    @GetMapping(path = "/{patientId}", headers = "X-API-VERSION=1")
    public List<NoteDto> getNotesByPatientId(@PathVariable Integer patientId) {
        log.info("====> GET /notes/{} <====", patientId);
        return noteService.findByPatientId(patientId);
    }

}
