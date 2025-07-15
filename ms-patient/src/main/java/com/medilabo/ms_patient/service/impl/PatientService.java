package com.medilabo.ms_patient.service.impl;

import com.medilabo.ms_patient.dto.PatientDto;
import com.medilabo.ms_patient.entity.Patient;
import com.medilabo.ms_patient.exceptions.PatientNotFoundException;
import com.medilabo.ms_patient.mapper.PatientMapper;
import com.medilabo.ms_patient.repository.PatientRepository;
import com.medilabo.ms_patient.service.IPatientService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The PatientService class implements the IPatientService interface
 * and provides methods for managing patients.
 */
@Service
public class PatientService implements IPatientService {

    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;

    /**
     * Constructor for PatientService.
     *
     * @param patientRepository the repository for patient entities
     */
    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    /**
     * Finds all patients in the repository (only returns non-deleted patients).
     *
     * @return a list of all patients
     */

    @Override
    public List<Patient> findAll() {
        return patientRepository.findByDeletedFalse();
    }

    /**
     * Finds a patient by ID (only returns non-deleted patients).
     *
     * @param id the ID of the patient to find
     * @return the patient with the specified ID
     * @throws PatientNotFoundException if no patient with the specified ID is found
     */
    @Override
    public Patient findById(Integer id) throws PatientNotFoundException {
        return patientRepository.findByPatientIdAndDeletedFalse(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    /**
     * Delete a patient by ID (Patient is marked as deleted).
     *
     * @param id the ID of the patient to delete
     * @throws PatientNotFoundException if no patient with the specified ID is found
     */
    @Override
    public void deleteById(Integer id) throws PatientNotFoundException {
        patientRepository.findById(id).ifPresentOrElse(
                patient -> {
                    patient.setDeleted(true);
                    patientRepository.save(patient);
                },
                () -> {
                    throw new PatientNotFoundException(id);
                }
        );
    }

    /**
     * Creates a new patient.
     *
     * @param patientDto the data transfer object containing patient information
     * @return the created Patient entity
     */
    @Override
    public Patient create(PatientDto patientDto) {
        Patient patient = patientMapper.toPatient(patientDto);
        return patientRepository.save(patient);
    }

    /**
     * Updates an existing patient.
     *
     * @param patientId  the ID of the patient to update
     * @param patientDto the data transfer object containing updated patient information
     * @return the updated Patient entity
     * @throws PatientNotFoundException if no patient with the specified ID is found
     */
    @Override
    public Patient update(Integer patientId, PatientDto patientDto) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));

        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setBirthDate(patientDto.getBirthDate());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setPhoneNumber(patientDto.getPhoneNumber());

        return patientRepository.save(patient);
    }
}
