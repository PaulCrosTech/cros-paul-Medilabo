package com.medilabo.ms_note.service.impl;

import com.medilabo.ms_note.entity.Note;
import com.medilabo.ms_note.exception.NoteNotFoundException;
import com.medilabo.ms_note.repository.NoteRepository;
import com.medilabo.ms_note.service.INoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The NoteService class implements the INoteService interface
 */
@Service
@Slf4j
public class NoteService implements INoteService {

    private final NoteRepository noteRepository;

    /**
     * Constructor for NoteService.
     *
     * @param noteRepository the repository to handle note operations
     */
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        log.info("====> NoteService initialized <====");
    }

    /**
     * Find notes by patient ID.
     *
     * @param patientId the ID of the patient
     * @return a list of NoteDto
     */
    @Override
    public List<Note> findByPatientId(Integer patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    /**
     * Delete a note by ID (Note is marked as deleted).
     *
     * @param id the ID of the note to delete
     * @throws NoteNotFoundException if no note with the specified ID is found
     */
    @Override
    public void deleteById(String id) throws NoteNotFoundException {
        noteRepository.findById(id).ifPresentOrElse(
                noteRepository::delete,
                () -> {
                    throw new NoteNotFoundException(id);
                }
        );
    }

}
