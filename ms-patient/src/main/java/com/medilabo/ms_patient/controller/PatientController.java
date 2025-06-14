package com.medilabo.ms_patient.controller;

import com.medilabo.ms_patient.entity.Patient;
import com.medilabo.ms_patient.service.IPatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for patient-related operations.
 */
@RestController
@Slf4j
@RequestMapping(path = "/patients")
public class PatientController {

    private final IPatientService patientService;

    /**
     * Constructor for PatientController.
     *
     * @param patientService the service to handle patient operations
     */
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
        log.info("====> PatientController initialized <====");
    }


    /**
     * Retrieves a list of all patients.
     *
     * @return a list of patients
     */
    @GetMapping(headers = "X-API-VERSION=1")
    public List<Patient> getPatients() {
        log.info("====> GET /patients : page <====");
        return patientService.findAll();
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param id the ID of the patient
     * @return the patient with the specified ID
     */
    @GetMapping(path = "/{id}", headers = "X-API-VERSION=1")
    public Patient getPatientById(@PathVariable int id) {
        log.info("====> GET /patients/{} <====", id);
        return patientService.findById(id);
    }
}
