package com.medilabo.ms_patient.entity;

import com.medilabo.ms_patient.utils.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity of the Patient
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    //    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Gender gender;

    @Column(nullable = false, columnDefinition = "ENUM('M', 'F')")
    private String gender;


    @Column(length = 100)
    private String address;

    @Column(name = "phone_number", length = 12)
    private String phoneNumber;

}
