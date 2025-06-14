package com.medilabo.ms_patient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping(path = "/patients")
public class PatientController {

    @GetMapping(headers = "X-API-VERSION=1")
    public String getVersion() {
        log.info("====> GET /patients : page <====");
        return "Patient API Version 1.0";
    }

}
