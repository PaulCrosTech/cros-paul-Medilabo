package com.medilabo.ms_riskassessment.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom error decoder for handling Feign client errors.
 * This class implements the ErrorDecoder interface from Feign.
 * It can be used to customize error handling logic when making requests with Feign clients.
 */
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    /**
     * Decode method to handle errors based on the HTTP response status.
     *
     * @param invoquer the method name that caused the error
     * @param response the HTTP response received from the Feign client
     * @return an Exception based on the response status
     */
    @Override
    public Exception decode(String invoquer, Response response) {

        if (invoquer.contains("MsPatientProxy#getByPatientId")) {
            if (response.status() == 404) {

                try {
                    URI uri = new URI(response.request().url());

                    Pattern pattern = Pattern.compile("/patients/(\\d+)");
                    Matcher matcher = pattern.matcher(uri.getPath());
                    if (!matcher.find()) {
                        throw new URISyntaxException(uri.toString(), "Invalid URI format");
                    }
                    String patientId = matcher.group(1);
                    return new PatientNotFoundException(Integer.parseInt(patientId));
                } catch (URISyntaxException ex) {
                    return new PatientNotFoundException(-1);
                }
            }
        } else if (invoquer.contains("MsNoteProxy#getByPatientId")) {
            if (response.status() == 404) {

                try {
                    URI uri = new URI(response.request().url());

                    Pattern pattern = Pattern.compile("/notes/patient/(\\d+)");
                    Matcher matcher = pattern.matcher(uri.getPath());
                    if (!matcher.find()) {
                        throw new URISyntaxException(uri.toString(), "Invalid URI format");
                    }
                    String patientId = matcher.group(1);
                    return new PatientNotFoundException(Integer.parseInt(patientId));
                } catch (URISyntaxException ex) {
                    return new PatientNotFoundException(-1);
                }
            }
        }

        return defaultErrorDecoder.decode(invoquer, response);
    }
}
