package com.medilabo.ms_note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Note entity.
 * This class is used to transfer note data between layers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

    private String patientId;
    private String lastName;
    private String note;
}
