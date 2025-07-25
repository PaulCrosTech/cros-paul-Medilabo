package com.medilabo.ms_riskassessment.exception;


import com.medilabo.ms_riskassessment.utils.CustomApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomRestExceptionHandler Class
 */
@ControllerAdvice
@Slf4j
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * 400 BAD REQUEST
     * Handle exceptions throw by errors during object validation
     *
     * @param ex      Exception
     * @param headers HttpHeaders
     * @param status  HttpStatusCode
     * @param request WebRequest
     * @return ResponseEntity Object containing the error details and an HTTP status of BAD REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatusCode status,
                                                                  final WebRequest request) {
        log.error("<exception> MethodArgumentNotValidException : {}", ex.getMessage());


        final Map<String, String> errors = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }

        return sendResponseError(request, errors, HttpStatus.BAD_REQUEST);
    }

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
