package com.medilabo.ms_riskassessment.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Bean class representing a Note.
 * This class is used to transfer note data within the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteBean {
    
    private String note;

}
