package com.medilabo.ms_riskassessment.controller;

import com.medilabo.ms_riskassessment.domain.RiskLevel;
import com.medilabo.ms_riskassessment.exception.PatientNotFoundException;
import com.medilabo.ms_riskassessment.service.impl.RiskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for RiskController.
 */
@WebMvcTest(controllers = RiskController.class)
public class RiskControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RiskService riskService;

    /**
     * Test method: calculateRiskOfPatientId
     * Given: a patient ID
     * When: GET /risks/{patientId}
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenExistingPatient_whenCalculateRiskOfPatientId_thenReturnOk() throws Exception {
        // Given
        int patientId = 1;
        when(riskService.calculateRiskOfPatientId(patientId)).thenReturn(any(RiskLevel.class));

        // When & Then
        mockMvc.perform(get("/risks/{patientId}", patientId)
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isOk());
    }

    /**
     * Test method: getAllRiskAssessments
     * Given: Unknown patient ID
     * When: GET /risks
     * Then: Return status Not Found (404)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void givenNonExistingPatient_whenCalculateRiskOfPatientId_thenReturnNotFound() throws Exception {
        // Given
        int patientId = 999;
        doThrow(new PatientNotFoundException(patientId)).when(riskService).calculateRiskOfPatientId(patientId);

        // When & Then
        mockMvc.perform(get("/risks/{patientId}", patientId)
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test method: getAllRiskAssessments
     * Given: a request to get all risk assessments
     * When: GET /risks
     * Then: Return status OK (200)
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void whenGetAllRiskAssessments_thenReturnOk() throws Exception {
        // Given
        Map<String, String> riskAssessments = Map.of();
        when(riskService.getAll()).thenReturn(riskAssessments);

        // When & Then
        mockMvc.perform(get("/risks")
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isOk());
    }


}
