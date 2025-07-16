package com.medilabo.ms_note.service.impl;

import com.medilabo.ms_note.dto.NoteDto;
import com.medilabo.ms_note.mapper.NoteMapper;
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
    private final NoteMapper noteMapper;

    /**
     * Constructor for NoteService.
     *
     * @param noteRepository the repository to handle note operations
     */
    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        log.info("====> NoteService initialized <====");
    }

    /**
     * Find notes by patient ID.
     *
     * @param patientId the ID of the patient
     * @return a list of NoteDto
     */
    @Override
    public List<NoteDto> findByPatientId(Integer patientId) {
        return noteRepository.findByPatientId(patientId).stream()
                .map(noteMapper::toNoteDto)
                .toList();
    }

}
