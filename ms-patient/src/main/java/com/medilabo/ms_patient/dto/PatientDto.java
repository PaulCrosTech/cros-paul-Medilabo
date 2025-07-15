package com.medilabo.ms_patient.dto;

import com.medilabo.ms_patient.validators.annotations.ValidBirthDate;
import com.medilabo.ms_patient.validators.annotations.ValidGender;
import com.medilabo.ms_patient.validators.annotations.ValidPhoneNumber;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * Data Transfer Object (DTO) for Patient entity.
 * This class is used to transfer patient data between layers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    @NotEmpty(message = "First name is mandatory.")
    @Length(max = 45, message = "First name must be between 1 and 45 characters.")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory.")
    @Length(max = 45, message = "Last name must be between 1 and 45 characters.")
    private String lastName;

    @NotNull(message = "Birth date is mandatory.")
    @ValidBirthDate
    private String birthDate;

    @NotNull(message = "Gender is mandatory.")
    @ValidGender
    private String gender;

    @Length(max = 100, message = "Address must be at most 100 characters.")
    private String address;

    @ValidPhoneNumber
    private String phoneNumber;
}
