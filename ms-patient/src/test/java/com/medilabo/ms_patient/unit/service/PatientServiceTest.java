package com.medilabo.ms_patient.unit.service;

import com.medilabo.ms_patient.dto.PatientDto;
import com.medilabo.ms_patient.entity.Patient;
import com.medilabo.ms_patient.exceptions.PatientNotFoundException;
import com.medilabo.ms_patient.mapper.PatientMapper;
import com.medilabo.ms_patient.repository.PatientRepository;
import com.medilabo.ms_patient.service.impl.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the PatientService class.
 */
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientMapper patientMapper;

    private List<Patient> patients;

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {
        patientService = new PatientService(patientRepository, patientMapper);

        patients = List.of(
                new Patient(1, "John", "Doe", "2023-01-01", "M", "123 Main St", "123-123-1234"),
                new Patient(2, "Emilia", "Apple", "1990-12-31", "F", "333 Par. St", "222-333-4444")
        );
    }

    /**
     * Test method: findAll
     * Given: list of patients
     * When: findAll
     * Then: Return list of patients
     */
    @Test
    public void givenPatients_whenFindAll_thenReturnList() {
        // Given
        when(patientRepository.findAll()).thenReturn(patients);


        // When
        List<Patient> actualPatients = patientService.findAll();

        // Then
        assertEquals(patients, actualPatients);
    }

    /**
     * Test method: findById
     * Given: a patient with ID 1
     * When: findById
     * Then: Return patient with ID 1
     */
    @Test
    public void givenPatientId_whenFindById_thenReturnPatient() {
        // Given
        Patient patient = patients.getFirst();
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        // When
        Patient actualPatient = patientService.findById(1);

        // Then
        assertEquals(patient, actualPatient);
        assertEquals(patient.getPatientId(), actualPatient.getPatientId());
    }

    /**
     * Test method: findById
     * Given: a non-existent patient ID
     * When: findById
     * Then: Throw PatientNotFoundException
     */
    @Test
    public void givenNonExistentPatientId_whenFindById_thenThrowException() {
        // Given
        when(patientRepository.findById(999)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                PatientNotFoundException.class,
                () -> patientService.findById(999)
        );
    }

    /**
     * Test method: deleteById
     * Given: a patient with ID 1
     * When: deleteById
     * Then: Patient is deleted
     */
    @Test
    public void givenPatientId_whenDeleteById_thenPatientDeleted() {
        // Given
        int patientId = 1;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patients.getFirst()));

        // When
        patientService.deleteById(patientId);

        // Then
        verify(patientRepository, times(1)).deleteById(patientId);
    }

    /**
     * Test method: deleteById
     * Given: a non-existent patient ID
     * When: deleteById
     * Then: Throw PatientNotFoundException
     */
    @Test
    public void givenNonExistentPatientId_whenDeleteById_thenThrowException() {
        // Given
        int patientId = 999;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                PatientNotFoundException.class,
                () -> patientService.deleteById(patientId)
        );
    }

    /**
     * Test method: create
     * Given: a new patient DTO
     * When: create
     * Then: Return created patient
     */
    @Test
    public void givenNewPatientDto_whenCreate_thenReturnCreatedPatient() {
        // Given
        PatientDto patientDto = new PatientDto("Alice", "Smith", "1995-05-15", "F", "456 Elm St", "555-555-5555");

        Patient patient = new Patient();
        patient.setPatientId(3);
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setBirthDate(patientDto.getBirthDate());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setPhoneNumber(patientDto.getPhoneNumber());

        when(patientMapper.toPatient(patientDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);

        // When
        Patient createdPatient = patientService.create(patientDto);

        // Then
        assertEquals(patient, createdPatient);
    }


    /**
     * Test method: update
     * Given: a patient with ID 1 and updated patient DTO
     * When: update
     * Then: Return updated patient
     */
    @Test
    public void givenPatientDto_whenUpdate_thenReturnUpdatedPatient() {
        // Given
        int patientId = 1;
        PatientDto patientDto = new PatientDto("JohnUpdated", "DoeUpdated", "2023-01-01", "M", "123 Main St", "123-123-1234");
        Patient patient = patients.getFirst();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);


        // When
        Patient actualPatient = patientService.update(patientId, patientDto);

        // Then
        assertEquals(patientId, actualPatient.getPatientId());
        assertEquals(patientDto.getFirstName(), actualPatient.getFirstName());
        assertEquals(patientDto.getLastName(), actualPatient.getLastName());
        assertEquals(patientDto.getBirthDate(), actualPatient.getBirthDate());
        assertEquals(patientDto.getGender(), actualPatient.getGender());
        assertEquals(patientDto.getAddress(), actualPatient.getAddress());
        assertEquals(patientDto.getPhoneNumber(), actualPatient.getPhoneNumber());
        verify(patientRepository, times(1)).save(patient);
    }


    /**
     * Test method: update
     * Given: a non-existent patient ID and updated patient DTO
     * When: update
     * Then: Throw PatientNotFoundException
     */
    @Test
    public void givenNonExistentPatientDto_whenUpdate_thenThrowException() {
        // Given
        int patientId = 999;
        PatientDto patientDto = new PatientDto("NonExistent", "Patient", "2000-01-01", "M", "Nonexistent St", "000-000-0000");

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                PatientNotFoundException.class,
                () -> patientService.update(patientId, patientDto)
        );
    }
}
