package com.medilabo.ms_patient.repository;


import com.medilabo.ms_patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The PatientRepository interface extends CrudRepository to provide CRUD operations for Patient entities.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}