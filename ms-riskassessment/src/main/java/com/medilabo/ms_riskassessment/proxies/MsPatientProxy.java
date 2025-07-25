package com.medilabo.ms_riskassessment.proxies;

import com.medilabo.ms_riskassessment.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Proxy interface for interacting with the Patient microservice.
 * This interface uses Feign to make HTTP requests to the Patient service.
 */
@FeignClient(name = "ms-patient")
public interface MsPatientProxy {

    @GetMapping(value = "/patients/{patientId}", headers = "X-API-VERSION=1")
    PatientBean getByPatientId(@PathVariable("patientId") Integer patientId);
}
