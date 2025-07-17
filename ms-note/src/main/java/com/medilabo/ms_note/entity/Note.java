package com.medilabo.ms_note.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Entity class representing a Note in the MongoDB database.
 * This class is used to store notes related to patients.
 */
@Document(collection = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    private String id;

    private Integer patientId;

    private String lastName;

    private String note;

    private Date createdAt = new Date();

}
