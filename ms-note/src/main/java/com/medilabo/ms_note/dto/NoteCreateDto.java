package com.medilabo.ms_note.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating a Note.
 * This class is used to transfer note creation data between layers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteCreateDto {

    @NotNull(message = "Patient ID is mandatory.")
    private Integer patientId;

    @NotEmpty(message = "Note is mandatory.")
    private String note;
}
