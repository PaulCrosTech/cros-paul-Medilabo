package com.medilabo.ms_patient.validators;

import com.medilabo.ms_patient.validators.annotations.ValidGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class GenderValidator implements ConstraintValidator<ValidGender, String> {


    @Override
    public void initialize(ValidGender constraintAnnotation) {

    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {

        // If the field is null, we consider it valid (this allows for optional fields)
        if (gender == null || gender.equals("M") || gender.equals("F")) {
            return true;
        }
        return false;
    }


}
