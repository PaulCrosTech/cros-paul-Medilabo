package com.medilabo.ms_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main application class for the Medilabo Eureka Service.
 */
@SpringBootApplication
@EnableEurekaServer
public class MsEurekaApplication {

    /**
     * The entry point of the Medilabo Eureka Service application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsEurekaApplication.class, args);
    }

}
