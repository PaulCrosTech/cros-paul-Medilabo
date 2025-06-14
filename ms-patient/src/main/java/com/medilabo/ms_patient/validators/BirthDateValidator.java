package com.medilabo.ms_patient.validators;


import com.medilabo.ms_patient.validators.annotations.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateValidator Class
 */
public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {

    private String pattern;

    /**
     * Initialize the pattern
     *
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidBirthDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }


    @Override
    public boolean isValid(String birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return true; // null is valid, use @NotNull for null checks
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);

        try {
            Date parsedDate = sdf.parse(birthDate);
            if (parsedDate.after(new Date())) {
                return false;
            }

        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}