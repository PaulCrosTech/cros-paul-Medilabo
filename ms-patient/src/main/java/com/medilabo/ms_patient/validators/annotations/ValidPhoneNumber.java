package com.medilabo.ms_patient.validators.annotations;

import com.medilabo.ms_patient.validators.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidPhoneNumber Annotation
 * This annotation is used to validate phone numbers.
 * The phone number must match the specified pattern.
 */
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPhoneNumber {

    /**
     * message
     *
     * @return String
     */
    String message() default "The phone number must be in the format XXX-XXX-XXXX.";

    /**
     * pattern
     *
     * @return String
     */
    String pattern() default "^\\d{3}-\\d{3}-\\d{4}$";

    /**
     * groups
     *
     * @return Class
     */
    Class<?>[] groups() default {};

    /**
     * payload
     *
     * @return Class
     */
    Class<? extends Payload>[] payload() default {};

}
