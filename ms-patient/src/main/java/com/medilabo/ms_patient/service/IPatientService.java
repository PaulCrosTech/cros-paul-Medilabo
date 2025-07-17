package com.medilabo.ms_patient.service;

import com.medilabo.ms_patient.dto.PatientDto;
import com.medilabo.ms_patient.entity.Patient;
import com.medilabo.ms_patient.exceptions.PatientNotFoundException;

import java.util.List;

/**
 * The interface IPatientService defines the operations for managing patients.
 */
public interface IPatientService {


    /**
     * Find all patients (only returns non-deleted patients).
     *
     * @return a list of all patients
     */
    List<Patient> findAll();

    /**
     * Find a patient by ID (only returns non-deleted patients).
     *
     * @param id the ID of the patient to find
     * @return the patient with the specified ID
     * @throws PatientNotFoundException if no patient with the specified ID is found
     */
    Patient findById(Integer id) throws PatientNotFoundException;

    /**
     * Find a patient by ID and return only the last name (only returns non-deleted patients).
     *
     * @param id the ID of the patient to find
     * @return the last name of the patient with the specified ID
     * @throws PatientNotFoundException if no patient with the specified ID is found
     */
    String findLastNameById(Integer id) throws PatientNotFoundException;


    /**
     * Delete a patient by ID (Patient is marked as deleted).
     *
     * @param id the ID of the patient to delete
     * @throws PatientNotFoundException if no patient with the specified ID is found
     */
    void deleteById(Integer id) throws PatientNotFoundException;

    /**
     * Create a new patient.
     *
     * @param patientDto the data transfer object containing patient information
     * @return the created Patient entity
     */
    Patient create(PatientDto patientDto);

    /**
     * Update an existing patient.
     *
     * @param patientId  the ID of the patient to update
     * @param patientDto the data transfer object containing updated patient information
     * @return the updated Patient entity
     * @throws PatientNotFoundException if no patient with the specified ID is found
     */
    Patient update(Integer patientId, PatientDto patientDto) throws PatientNotFoundException;


}
