package com.medilabo.ms_patient.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * PatientNotFoundException Class
 * This exception is thrown when a patient is not found in the system.
 */
@Slf4j
public class PatientNotFoundException extends RuntimeException {
    /**
     * Constructor for PatientNotFoundException.
     *
     * @param message the detail message
     */
    public PatientNotFoundException(String message) {
        super(message);
        log.error("====> <exception> PatientNotFoundException : {} <====", message);
    }
}
