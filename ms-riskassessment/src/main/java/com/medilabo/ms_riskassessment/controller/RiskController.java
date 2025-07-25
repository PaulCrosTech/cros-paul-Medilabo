package com.medilabo.ms_riskassessment.controller;

import com.medilabo.ms_riskassessment.domain.RiskLevel;
import com.medilabo.ms_riskassessment.service.IRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for risk assessment-related operations.
 */
@RestController
@Slf4j
@RequestMapping(path = "/risks")
public class RiskController {

    private final IRiskService riskService;

    /**
     * Constructor for RiskController.
     */
    public RiskController(IRiskService riskService) {
        this.riskService = riskService;
        log.info("====> RiskController initialized <====");
    }

    /**
     * Retrieves the risk assessment for a patient by their ID.
     *
     * @param patientId the ID of the patient
     * @return a string representing the risk assessment data
     */
    @GetMapping(path = "/{patientId}", headers = "X-API-VERSION=1")
    public RiskLevel calculateRiskOfPatientId(@PathVariable int patientId) {
        log.info("====> GET /risks/{} <====", patientId);
        return riskService.calculateRiskOfPatientId(patientId);
    }

    @GetMapping(path = "", headers = "X-API-VERSION=1")
    public Map<String, String> getAllRiskAssessments() {
        log.info("====> GET /risks <====");
        return riskService.getAll();
    }
}
