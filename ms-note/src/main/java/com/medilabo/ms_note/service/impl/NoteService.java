package com.medilabo.ms_note.service.impl;

import com.medilabo.ms_note.beans.PatientBean;
import com.medilabo.ms_note.dto.NoteCreateDto;
import com.medilabo.ms_note.entity.Note;
import com.medilabo.ms_note.exception.NoteNotFoundException;
import com.medilabo.ms_note.exception.PatientNotFoundException;
import com.medilabo.ms_note.mapper.NoteMapper;
import com.medilabo.ms_note.proxies.MsPatientProxy;
import com.medilabo.ms_note.repository.NoteRepository;
import com.medilabo.ms_note.service.INoteService;
import feign.FeignException;
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
    private final MsPatientProxy msPatientProxy;

    /**
     * Constructor for NoteService.
     *
     * @param noteRepository the repository to handle note operations
     */
    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper, MsPatientProxy msPatientProxy) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.msPatientProxy = msPatientProxy;
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

    /**
     * Create a new note.
     *
     * @param noteCreateDto the note to create
     * @return the created Note
     */
    @Override
    public Note create(NoteCreateDto noteCreateDto) throws PatientNotFoundException {

        String patientBean = msPatientProxy.getPatientLastNameById(noteCreateDto.getPatientId());
        log.info("====> patientBean {} <====", patientBean);

        Note note = noteMapper.NoteCreateDtoToNote(noteCreateDto);
        note.setLastName(patientBean);
        log.info("====> Note created {} <====", note);
        return noteRepository.save(note);

    }

}
