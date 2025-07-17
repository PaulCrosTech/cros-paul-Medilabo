package com.medilabo.ms_patient.repository;


import com.medilabo.ms_patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The PatientRepository interface extends CrudRepository to provide CRUD operations for Patient entities.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByDeletedFalse();

    Optional<Patient> findByPatientIdAndDeletedFalse(Integer id);

    @Query("SELECT lastName FROM Patient WHERE patientId = :id AND deleted = false")
    Optional<String> findLastNameByPatientIdAndDeletedFalse(Integer id);
}