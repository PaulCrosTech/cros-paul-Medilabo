package com.medilabo.ms_riskassessment.proxies;

import com.medilabo.ms_riskassessment.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Proxy interface for interacting with the Note microservice.
 * This interface uses Feign to make HTTP requests to the Note service.
 */
@FeignClient(name = "ms-note")
public interface MsNoteProxy {

    @GetMapping(value = "/notes/patient/{patientId}", headers = "X-API-VERSION=1")
    List<NoteBean> getByPatientId(@PathVariable("patientId") Integer patientId);
}
