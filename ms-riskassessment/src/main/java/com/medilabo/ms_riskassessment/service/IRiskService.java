package com.medilabo.ms_riskassessment.service;

import com.medilabo.ms_riskassessment.domain.RiskLevel;

import java.util.Map;

/**
 * Interface for risk-assessment related operations.
 */
public interface IRiskService {

    /**
     * Retrieves the risk assessment for a patient by their ID.
     *
     * @param patientId the ID of the patient
     * @return a string representing the risk assessment data
     */
    RiskLevel calculateRiskOfPatientId(int patientId);

    /**
     * Retrieves all risk assessments by ordering them by risk level.
     *
     * @return a List of risk assessment
     */
    Map<String, String> getAll();

}
