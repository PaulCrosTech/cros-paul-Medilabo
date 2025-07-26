package com.medilabo.ms_riskassessment.exception;

import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CustomErrorDecoder.
 */
public class CustomErrorDecoderTest {
    private final CustomErrorDecoder customErrorDecoder = new CustomErrorDecoder();


    /**
     * Test method: decode
     * Given: a response with status 500 and a not registered invoquer
     * When: decode is called
     * Then: Return FeignException
     */
    @Test
    void givenNotRegisterdInvoquer_whenDecode_thenReturnFeignException() {
        // Given
        String url = "http://localhost/patients/123";
        Response response = Response.builder()
                .status(500)
                .request(Request.create(Request.HttpMethod.GET, url, Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        // When
        Exception exception = customErrorDecoder.decode("NotRegisterClass#NotRegisterMethod", response);

        // Then
        assertInstanceOf(FeignException.class, exception);
    }

    /**
     * Test method: decode
     * Given: a 404 response from MsPatientProxy.getByPatientId
     * When: decode is called
     * Then: Throw PatientNotFoundException
     */
    @Test
    void given404FromMsPatientProxyGetByPatientId_whenDecode_thenThrowPatientNotFoundException() {
        // Given
        String url = "http://localhost/patients/1";
        Response response = Response.builder()
                .status(404)
                .request(Request.create(Request.HttpMethod.GET, url, Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        // When
        Exception exception = customErrorDecoder.decode("MsPatientProxy#getByPatientId", response);

        // Then
        assertInstanceOf(PatientNotFoundException.class, exception);
    }


    /**
     * Test method: decode
     * Given: a 404 response from MsNoteProxy.getByPatientId
     * When: decode is called
     * Then: Throw PatientNotFoundException
     */
    @Test
    void given404FromMsNoteProxyGetByPatientId_whenDecode_thenThrowPatientNotFoundException() {
        // Given
        String url = "http://localhost/notes/patient/1";
        Response response = Response.builder()
                .status(404)
                .request(Request.create(Request.HttpMethod.GET, url, Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        // When
        Exception exception = customErrorDecoder.decode("MsNoteProxy#getByPatientId", response);

        // Then
        assertInstanceOf(PatientNotFoundException.class, exception);
    }

    /**
     * Test method: decode
     * Given: an invalid URI format in the response
     * When: decode is called
     * Then: Return PatientNotFoundException with default ID
     */
    @Test
    void givenInvalidURIFromMsPatientProxyGetByPatientId_whenDecode_thenReturnPatientNotFoundExceptionWithDefaultId() {
        // Given
        String url = "http://localhost/invalid/uri";
        Response response = Response.builder()
                .status(404)
                .request(Request.create(Request.HttpMethod.GET, url, Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        // When
        Exception exception = customErrorDecoder.decode("MsPatientProxy#getByPatientId", response);

        // Then
        assertInstanceOf(PatientNotFoundException.class, exception);
    }

    /**
     * Test method: decode
     * Given: an invalid URI format in the response
     * When: decode is called
     * Then: Return PatientNotFoundException with default ID
     */
    @Test
    void givenInvalidURIFromMsNoteProxyGetByPatientId_whenDecode_thenReturnPatientNotFoundExceptionWithDefaultId() {
        // Given
        String url = "http://localhost/invalid/uri";
        Response response = Response.builder()
                .status(404)
                .request(Request.create(Request.HttpMethod.GET, url, Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        // When
        Exception exception = customErrorDecoder.decode("MsNoteProxy#getByPatientId", response);

        // Then
        assertInstanceOf(PatientNotFoundException.class, exception);
    }

}
