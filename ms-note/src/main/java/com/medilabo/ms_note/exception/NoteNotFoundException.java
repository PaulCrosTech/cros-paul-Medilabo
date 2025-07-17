package com.medilabo.ms_note.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when a note is not found.
 * This exception is used to indicate that a note with the specified ID does not exist.
 */
@Slf4j
public class NoteNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Note not found with id: ";

    /**
     * Constructs a new NoteNotFoundException with the specified ID.
     *
     * @param id the ID of the note that was not found
     */
    public NoteNotFoundException(String id) {
        super(DEFAULT_MESSAGE + id);
        log.error("====> <exception> NoteNotFoundException : {} <====", DEFAULT_MESSAGE + id);
    }
}
