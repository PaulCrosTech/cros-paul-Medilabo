package com.medilabo.ms_note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Note Microservice
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = "com.medilabo.ms_note.proxies")
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
