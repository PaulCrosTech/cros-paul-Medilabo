package com.medilabo.ms_patient.utils;


import lombok.Getter;

/**
 * Gender enumeration
 */
@Getter
public enum Gender {

    M("Masculin"),
    F("Féminin");

    private final String description;

    /**
     * Constructor
     *
     * @param description Description
     */
    Gender(String description) {
        this.description = description;
    }

}
