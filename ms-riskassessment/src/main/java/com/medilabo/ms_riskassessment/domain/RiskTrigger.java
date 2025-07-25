package com.medilabo.ms_riskassessment.domain;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum representing different risk triggers for patients.
 */
@Getter
public enum RiskTrigger {
    HEMOGLOBINE_A1C("Hémoglobine A1C"),
    MICROALBUMINE("Microalbumine"),
    TAILLE("Taille"),
    POIDS("Poids"),
    FUMEUR("Fumeur"),
    FUMEUSE("Fumeuse"),
    ANORMAL("Anormal"),
    CHOLESTEROL("Cholestérol"),
    VERTIGES("Vertiges"),
    RECHUTE("Rechute"),
    REACTION("Réaction"),
    ANTICORPS("Anticorps");

    private final String label;

    /**
     * Constructor for RiskTrigger enum.
     *
     * @param label the string representation of the risk trigger
     */
    RiskTrigger(String label) {
        this.label = label;
    }

    /**
     * Returns a list of all risk trigger labels.
     *
     * @return a List of labels for each risk trigger
     */
    public static List<String> getLabels() {

        return Stream.of(RiskTrigger.values())
                .map(RiskTrigger::getLabel)
                .collect(Collectors.toList());
    }

}
