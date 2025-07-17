package com.medilabo.ms_note.repository;

import com.medilabo.ms_note.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Note entities.
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    /**
     * Find notes by patient ID, ordered by creation date.
     *
     * @param patientId the ID of the patient
     * @return a list of Note entities
     */
    List<Note> findByPatientIdOrderByCreatedAtDesc(Integer patientId);
}
