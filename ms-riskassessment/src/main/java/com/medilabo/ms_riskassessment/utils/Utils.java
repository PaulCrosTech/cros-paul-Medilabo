package com.medilabo.ms_riskassessment.utils;

import java.time.LocalDate;
import java.time.Period;

/**
 * Utility class for common functions
 */
public class Utils {

    /**
     * Calculate age from birthdate.
     *
     * @param birthDate the birthdate of the patient
     * @return the age of the patient in years
     */
    public static int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

}
