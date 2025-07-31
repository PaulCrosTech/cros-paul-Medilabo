package com.medilabo.ms_riskassessment.utils;

import java.time.LocalDate;
import java.time.Period;

/**
 * Utility class for common functions
 */
public class Utils {


    /**
     * Calculates the age based on the given birthdate.
     *
     * @param birthDate the birthdate of the person
     * @return the age in years
     * @throws IllegalArgumentException if the birthdate is after the current date
     */
    public static int calculateAge(LocalDate birthDate) throws IllegalArgumentException {
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) {
            throw new IllegalArgumentException("Birth date is after today");
        }

        return Period.between(birthDate, today).getYears();
    }
}
