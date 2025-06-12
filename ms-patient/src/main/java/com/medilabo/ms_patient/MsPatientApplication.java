package com.medilabo.ms_patient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Patient Microservice
 */
@Slf4j
@SpringBootApplication
public class MsPatientApplication {

    /**
     * Main method to run the application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsPatientApplication.class, args);
        log.info("===> MsPatientApplication started <===");
    }

}
