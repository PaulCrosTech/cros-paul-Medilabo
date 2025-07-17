package com.medilabo.ms_note.service;

import com.medilabo.ms_note.dto.NoteCreateDto;
import com.medilabo.ms_note.entity.Note;
import com.medilabo.ms_note.exception.NoteNotFoundException;
import com.medilabo.ms_note.exception.PatientNotFoundException;

import java.util.List;

/**
 * The interface INoteService defines the operations for managing notes.
 */
public interface INoteService {

    /**
     * Find notes by patient ID.
     *
     * @param patientId the ID of the patient
     * @return a list of Note
     */
    List<Note> findByPatientIdOrderByCreatedAt(Integer patientId);

    /**
     * Delete a note by ID.
     *
     * @param id the ID of the note to delete
     */
    void deleteById(String id) throws NoteNotFoundException;

    /**
     * Create a new note.
     *
     * @param noteCreateDto the note to create
     * @return the created Note
     */
    Note create(NoteCreateDto noteCreateDto) throws PatientNotFoundException;
}
