package com.medilabo.ms_patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Patient Microservice
 */
@SpringBootApplication
public class MsPatientApplication {

    /**
     * Main method to run the application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsPatientApplication.class, args);
    }

}
