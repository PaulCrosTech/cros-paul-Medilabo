package com.medilabo.ms_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Medilabo Gateway Service.
 */
@SpringBootApplication
public class MsGatewayApplication {

    /**
     * The entry point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsGatewayApplication.class, args);
    }

}
