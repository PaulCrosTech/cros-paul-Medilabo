package com.medilabo.ms_riskassessment.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for Utils.
 */
public class UtilsTest {

    /**
     * Test method: calculateAge
     * Given: Birthdate in future
     * When: calculateAge
     * Then: Throws IllegalArgumentException
     */
    @Test
    public void givenInvalidBirthDate_whenCalculateAge_thenThrowsException() {

        // Given
        LocalDate dateInFuture = LocalDate.now().plusYears(1);

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            Utils.calculateAge(dateInFuture);
        });
    }

    /**
     * Test method: calculateAge
     * Given: Valid birthdate
     * When: calculateAge
     * Then: Returns correct age
     */
    @Test
    public void givenValidBirthDate_whenCalculateAge_thenReturnsCorrectAge() {
        // Given
        int expectedAge = 30;
        LocalDate birthDate = LocalDate.now().minusYears(expectedAge);

        // When
        int actualAge = Utils.calculateAge(birthDate);

        // Then
        assertEquals(expectedAge, actualAge);
    }
}
