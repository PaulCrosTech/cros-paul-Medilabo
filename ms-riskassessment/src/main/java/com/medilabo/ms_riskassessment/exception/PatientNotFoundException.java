package com.medilabo.ms_riskassessment.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * PatientNotFoundException Class
 * This exception is thrown when a patient is not found in the system.
 */
@Slf4j
public class PatientNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Patient not found with id: ";

    /**
     * Constructor for PatientNotFoundException.
     *
     * @param patientId the ID of the patient that was not found
     */
    public PatientNotFoundException(String patientId) {
        super(DEFAULT_MESSAGE + patientId);
        log.error("====> <exception> PatientNotFoundException : {} <====", DEFAULT_MESSAGE + patientId);
    }
}
