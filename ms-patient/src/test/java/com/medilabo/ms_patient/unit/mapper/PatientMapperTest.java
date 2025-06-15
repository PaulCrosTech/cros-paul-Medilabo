package com.medilabo.ms_patient.unit.mapper;

import com.medilabo.ms_patient.dto.PatientDto;
import com.medilabo.ms_patient.entity.Patient;
import com.medilabo.ms_patient.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for the PatientMapper class.
 */
@ExtendWith(MockitoExtension.class)
public class PatientMapperTest {

    private static PatientMapper patientMapper;

    private Patient patient;
    private PatientDto patientDto;

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setupPerTest() {

        patientMapper = Mappers.getMapper(PatientMapper.class);

        patient = new Patient(1, "John", "Doe", "2023-01-01", "M", "123 Main St", "123-123-1234");
        patientDto = new PatientDto("John", "Doe", "2023-01-01", "M", "123 Main St", "123-123-1234");
    }


    /**
     * Test toPatient
     * Given: A PatientDto
     * When: toPatient
     * Then: Return a Patient
     */
    @Test
    public void givenPatientDto_whenToPatient_thenReturnPatient() {
        // When
        Patient actualPatient = patientMapper.toPatient(patientDto);

        // Then
        assertEquals(patientDto.getFirstName(), actualPatient.getFirstName());
        assertEquals(patientDto.getLastName(), actualPatient.getLastName());
        assertEquals(patientDto.getBirthDate(), actualPatient.getBirthDate());
        assertEquals(patientDto.getGender(), actualPatient.getGender());
        assertEquals(patientDto.getAddress(), actualPatient.getAddress());
        assertEquals(patientDto.getPhoneNumber(), actualPatient.getPhoneNumber());
    }

    /**
     * Test toPatientDto
     * Given: A Patient
     * When: toPatientDto
     * Then: Return a PatientDto
     */
    @Test
    public void givenPatient_whenToPatientDto_thenReturnPatientDto() {
        // When
        PatientDto actualPatientDto = patientMapper.toPatientDto(patient);

        // Then
        assertEquals(patient.getFirstName(), actualPatientDto.getFirstName());
        assertEquals(patient.getLastName(), actualPatientDto.getLastName());
        assertEquals(patient.getBirthDate(), actualPatientDto.getBirthDate());
        assertEquals(patient.getGender(), actualPatientDto.getGender());
        assertEquals(patient.getAddress(), actualPatientDto.getAddress());
        assertEquals(patient.getPhoneNumber(), actualPatientDto.getPhoneNumber());
    }


    /**
     * Test toPatient
     * Given: A null Patient
     * When: toPatient
     * Then: Return null
     */
    @Test
    public void givenNullPatient_whenToPatient_thenReturnNull() {
        // When
        Patient actualPatient = patientMapper.toPatient(null);

        // Then
        assertNull(actualPatient);
    }

    /**
     * Test toPatientDto
     * Given: A null PatientDto
     * When: toPatientDto
     * Then: Return null
     */
    @Test
    public void givenNullPatientDto_whenToPatientDto_thenReturnNull() {
        // When
        PatientDto actualPatientDto = patientMapper.toPatientDto(null);

        // Then
        assertNull(actualPatientDto);
    }
}
