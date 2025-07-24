package com.medilabo.ms_riskassessment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for risk assessment-related operations.
 */
@RestController
@Slf4j
@RequestMapping(path = "/risk")
public class RiskController {

    /**
     * Constructor for RiskController.
     */
    public RiskController() {
        log.info("====> RiskController initialized <====");
    }

    /**
     * Retrieves the risk assessment for a patient by their ID.
     *
     * @param patientId the ID of the patient
     * @return a string representing the risk assessment data
     */
    @GetMapping(path = "/{patientId}", headers = "X-API-VERSION=1")
    public String getRiskAssessment(@PathVariable int patientId) {
        log.info("====> GET /risk/{} <====", patientId);
        return "Risk assessment data";
    }
}
