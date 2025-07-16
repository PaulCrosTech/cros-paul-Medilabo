package com.medilabo.ms_note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Note Microservice
 */
@Slf4j
@SpringBootApplication
public class MsNoteApplication {

    /**
     * Main method to run the application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsNoteApplication.class, args);
        log.info("===> MsNoteApplication started <===");
    }

}
