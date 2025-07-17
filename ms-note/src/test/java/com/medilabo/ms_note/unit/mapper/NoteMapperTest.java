package com.medilabo.ms_note.unit.mapper;

import com.medilabo.ms_note.dto.NoteCreateDto;
import com.medilabo.ms_note.entity.Note;
import com.medilabo.ms_note.mapper.NoteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for the NoteMapper class.
 */
@ExtendWith(MockitoExtension.class)
public class NoteMapperTest {

    private NoteMapper noteMapper;
    
    private NoteCreateDto noteCreateDto;

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setupPerTest() {

        noteMapper = Mappers.getMapper(NoteMapper.class);

        noteCreateDto = new NoteCreateDto(1, "Note Content");
    }

    /**
     * Test NoteCreateDtoToNote
     * Given: A NoteCreateDto
     * When: NoteCreateDtoToNote
     * Then: Return a Note with the correct fields set
     */
    @Test
    public void NoteCreateDtoToNote() {
        // When
        Note actualNote = noteMapper.NoteCreateDtoToNote(noteCreateDto);

        // Then
        assertEquals(noteCreateDto.getPatientId(), actualNote.getPatientId());
        assertEquals(noteCreateDto.getNote(), actualNote.getNote());
        assertNull(actualNote.getId());
        assertNull(actualNote.getLastName());
        assertEquals(new Date().getTime(), actualNote.getCreatedAt().getTime(), 1000);
    }
}
