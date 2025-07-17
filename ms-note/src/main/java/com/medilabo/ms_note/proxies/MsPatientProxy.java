package com.medilabo.ms_note.proxies;

import com.medilabo.ms_note.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Proxy interface for interacting with the Patient microservice.
 * This interface uses Feign to make HTTP requests to the Patient service.
 */
@FeignClient(name = "ms-patient", url = "http://localhost:9005")
public interface MsPatientProxy {

    /**
     * Retrieves patient lastName by his ID.
     *
     * @return patient lastName
     */
    @GetMapping(value = "/patients/{id}/lastname", headers = "X-API-VERSION=1")
    String getPatientLastNameById(@PathVariable("id") int patientId);
}
