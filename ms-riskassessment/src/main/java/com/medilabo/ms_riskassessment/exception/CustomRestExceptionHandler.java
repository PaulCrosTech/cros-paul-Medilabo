package com.medilabo.ms_riskassessment.exception;


import com.medilabo.ms_riskassessment.utils.CustomApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

/**
 * CustomRestExceptionHandler Class
 */
@ControllerAdvice
@Slf4j
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 404 NOT FOUND
     *
     * @param ex      RuntimeException
     * @param request WebRequest
     * @return ResponseEntity Object containing the error details and an HTTP status of NOT FOUND
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        log.error("<exception> handleNotFoundException : {}", ex.getMessage());
        Map<String, String> errors = Map.of("main", ex.getMessage());
        return sendResponseError(request, errors, HttpStatus.NOT_FOUND);
    }


    /**
     * Send response error
     *
     * @param request    WebRequest
     * @param errors     List<String>
     * @param HttpStatus HttpStatus
     * @return ResponseEntity Object containing the error details and an HTTP status
     */
    private ResponseEntity<Object> sendResponseError(WebRequest request, Map<String, String> errors, HttpStatus HttpStatus) {

        String path = request.getDescription(false).replace("uri=", "");

        final CustomApiError customApiError = new CustomApiError(HttpStatus.value(),
                HttpStatus.getReasonPhrase(),
                path,
                errors);

        return new ResponseEntity<>(customApiError, HttpStatus);
    }

}
