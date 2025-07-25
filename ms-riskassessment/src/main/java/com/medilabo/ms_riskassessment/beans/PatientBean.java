package com.medilabo.ms_riskassessment.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean class representing a Patient.
 * This class is used to transfer patient data within the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientBean {
    
    private String birthDate;

    private String gender;

}
