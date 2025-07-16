package com.medilabo.ms_note.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class representing a Note in the MongoDB database.
 * This class is used to store notes related to patients.
 */
@Document(collection = "notes")
@Data
public class Note {

    @Id
    private String id;

    private Integer patientId;

    private String lastName;

    private String note;

}
