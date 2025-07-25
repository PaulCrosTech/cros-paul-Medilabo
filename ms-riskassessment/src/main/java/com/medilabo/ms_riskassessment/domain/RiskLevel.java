package com.medilabo.ms_riskassessment.domain;

import lombok.Getter;

/**
 * Enum representing different risk levels for patients.
 */
@Getter
public enum RiskLevel {
    NA("NA"),
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("InDanger"),
    EARLY_ONSET("EarlyOnset");

    private final String label;

    /**
     * Constructor for RiskLevel enum.
     *
     * @param label the string representation of the risk level
     */
    RiskLevel(String label) {
        this.label = label;
    }

}
