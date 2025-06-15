package com.medilabo.ms_patient.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.ms_patient.controller.PatientController;
import com.medilabo.ms_patient.dto.PatientDto;
import com.medilabo.ms_patient.entity.Patient;
import com.medilabo.ms_patient.exceptions.PatientNotFoundException;
import com.medilabo.ms_patient.service.impl.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test class for the PatientController class.
 * Without security configuration
 */
@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    /**
     * Test method: getPatients
     * Given: empty list of patients
     * When: GET /patients
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */

    @Test
    public void givenEmptyList_whenGetPatients_thenReturnOk() throws Exception {

        // Given
        when(patientService.findAll()).thenReturn(emptyList());

        // When & Then
        mockMvc.perform(get("/patients").header("X-API-VERSION", "1")).andExpect(status().isOk());

    }


    /**
     * Test method: getPatientById
     * Given: an existing patient with ID 1
     * When: GET /patients/1
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenExistingPatient_whenGetPatientById_thenReturnOk() throws Exception {

        // Given
        Patient patient = new Patient();
        patient.setPatientId(1);

        when(patientService.findById(patient.getPatientId())).thenReturn(patient);

        // When & Then
        mockMvc.perform(get("/patients/" + patient.getPatientId()).header("X-API-VERSION", "1"))
                .andExpect(status().isOk());

    }

    /**
     * Test method: getPatientById
     * Given: a non-existing patient with ID 99
     * When: GET /patients/99
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingPatient_whenGetPatientById_thenReturnNotFound() throws Exception {

        // Given
        int patientId = 99;
        doThrow(new PatientNotFoundException(patientId)).when(patientService).findById(patientId);

        // When & Then
        mockMvc.perform(get("/patients/" + patientId).header("X-API-VERSION", "1"))
                .andExpect(status().isNotFound());

    }

    /**
     * Test method: deletePatientById
     * Given: a patient with ID 1
     * When: DELETE /patients/1
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenExistingPatient_whenDeletePatientById_thenReturnOk() throws Exception {

        // Given
        Patient patient = new Patient();
        patient.setPatientId(1);

        // When & Then
        mockMvc.perform(delete("/patients/" + patient.getPatientId()).header("X-API-VERSION", "1"))
                .andExpect(status().isOk());

    }

    /**
     * Test method: deletePatientById
     * Given: a non-existing patient with ID 99
     * When: DELETE /patients/99
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingPatient_whenDeletePatientById_thenReturnNotFound() throws Exception {

        // Given
        int patientId = 99;
        doThrow(new PatientNotFoundException(patientId)).when(patientService).deleteById(patientId);

        // When & Then
        mockMvc.perform(delete("/patients/" + patientId).header("X-API-VERSION", "1"))
                .andExpect(status().isNotFound());

    }

    /**
     * Test method: createPatient
     * Given: a valid patient DTO
     * When: POST /patients
     * Then: Return status Created (201)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenValidPatient_whenCreatePatient_thenReturnCreated() throws Exception {
        // Given
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        patientDto.setGender("M");
        patientDto.setBirthDate("2025-05-22");
        ObjectMapper objectMapper = new ObjectMapper();
        String patientDtoJson = objectMapper.writeValueAsString(patientDto);

        Patient patient = new Patient();
        patient.setPatientId(1);

        when(patientService.create(patientDto)).thenReturn(patient);

        // When & Then
        mockMvc.perform(post("/patients")
                        .header("X-API-VERSION", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientDtoJson)
                )
                .andExpect(status().isCreated());
    }

    /**
     * Test method: createPatient
     * Given: an invalid patient DTO (missing required fields)
     * When: POST /patients
     * Then: Return status Bad Request (400)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenInvalidPatient_whenCreatePatient_thenReturnBadRequest() throws Exception {
        // Given
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        ObjectMapper objectMapper = new ObjectMapper();
        String patientDtoJson = objectMapper.writeValueAsString(patientDto);

        Patient patient = new Patient();
        patient.setPatientId(1);

        when(patientService.create(patientDto)).thenReturn(patient);

        // When & Then
        mockMvc.perform(post("/patients")
                        .header("X-API-VERSION", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientDtoJson)
                )
                .andExpect(status().isBadRequest());
    }

    /**
     * Test method: updatePatient
     * Given: a valid patient ID and updated patient DTO
     * When: PUT /patients/{id}
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenValidPatient_whenUpdatePatient_thenReturnOk() throws Exception {
        // Given
        int patientId = 1;
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John Updated");
        patientDto.setLastName("Doe Updated");
        patientDto.setGender("M");
        patientDto.setBirthDate("2025-05-22");
        ObjectMapper objectMapper = new ObjectMapper();
        String patientDtoJson = objectMapper.writeValueAsString(patientDto);

        Patient patient = new Patient();
        patient.setPatientId(patientId);

        when(patientService.update(patientId, patientDto)).thenReturn(patient);

        // When & Then
        mockMvc.perform(put("/patients/" + patientId)
                        .header("X-API-VERSION", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientDtoJson)
                )
                .andExpect(status().isOk());
    }

    /**
     * Test method: updatePatient
     * Given: an invalid patient ID and updated patient DTO
     * When: PUT /patients/{id}
     * Then: Return status Bad Request (400)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenInvalidPatient_whenUpdatePatient_thenReturnBadRequest() throws Exception {
        // Given
        int patientId = 1;
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John Updated");
        patientDto.setLastName("Doe Updated");
        ObjectMapper objectMapper = new ObjectMapper();
        String patientDtoJson = objectMapper.writeValueAsString(patientDto);

        Patient patient = new Patient();
        patient.setPatientId(patientId);

        when(patientService.update(patientId, patientDto)).thenReturn(patient);

        // When & Then
        mockMvc.perform(put("/patients/" + patientId)
                        .header("X-API-VERSION", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientDtoJson)
                )
                .andExpect(status().isBadRequest());
    }

    /**
     * Test method: updatePatient
     * Given: a non-existing patient ID and updated patient DTO
     * When: PUT /patients/{id}
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingPatient_whenUpdatePatient_thenReturnNotFound() throws Exception {
        // Given
        int patientId = 99;
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John Updated");
        patientDto.setLastName("Doe Updated");
        patientDto.setGender("M");
        patientDto.setBirthDate("2025-05-22");
        ObjectMapper objectMapper = new ObjectMapper();
        String patientDtoJson = objectMapper.writeValueAsString(patientDto);

        doThrow(new PatientNotFoundException(patientId)).when(patientService).update(patientId, patientDto);

        // When & Then
        mockMvc.perform(put("/patients/" + patientId)
                        .header("X-API-VERSION", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientDtoJson)
                )
                .andExpect(status().isNotFound());
    }

}
