package com.medilabo.ms_note.utils;

import lombok.Data;

import java.util.Map;

/**
 * CustomApiError Class
 */
@Data
public class CustomApiError {

    private int status;
    private String message;
    private Map<String, String> errors;
    private String path;

    /**
     * Constructor
     *
     * @param status  status
     * @param message message
     * @param path    path
     * @param errors  list of errors
     */
    public CustomApiError(int status, String message, String path, Map<String, String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }
}
