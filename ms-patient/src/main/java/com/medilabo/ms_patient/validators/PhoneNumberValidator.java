package com.medilabo.ms_patient.validators;

import com.medilabo.ms_patient.validators.annotations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * PhoneNumberValidator
 * This class validates phone numbers based on a specified pattern.
 */
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private String pattern;


    /**
     * Initialize the pattern
     *
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }


    /**
     * Check if the phone number is valid (empty or null is considered valid).
     *
     * @param phoneNumber                the phone number
     * @param constraintValidatorContext the context
     * @return true if the phone number is valid
     */
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.matches(pattern);
    }

}
