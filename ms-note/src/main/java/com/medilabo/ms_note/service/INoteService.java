package com.medilabo.ms_note.service;

import com.medilabo.ms_note.dto.NoteDto;

import java.util.List;

/**
 * The interface INoteService defines the operations for managing notes.
 */
public interface INoteService {

    /**
     * Find notes by patient ID.
     *
     * @param patientId the ID of the patient
     * @return a list of NoteDto
     */
    List<NoteDto> findByPatientId(Integer patientId);
}
