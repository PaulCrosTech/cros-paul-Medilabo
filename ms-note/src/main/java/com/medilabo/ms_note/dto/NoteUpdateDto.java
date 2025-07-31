package com.medilabo.ms_note.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for updating a Note.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteUpdateDto {

    @NotEmpty(message = "Note is mandatory.")
    private String note;
}
