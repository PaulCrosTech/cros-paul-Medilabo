package com.medilabo.ms_riskassessment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Medilabo Risk Assessment Service.
 */
@SpringBootApplication
@Slf4j
public class MsRiskassessmentApplication {

    /**
     * Main method to run the Medilabo Risk Assessment Service.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsRiskassessmentApplication.class, args);
        log.info("===> MsRiskAssessmentApplication started <===");
    }

}
