package com.medilabo.ms_note.mapper;


import com.medilabo.ms_note.dto.NoteDto;
import com.medilabo.ms_note.entity.Note;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between NoteDto and Note entity.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper {

    /**
     * Converts a Note to a NoteDto
     *
     * @param note the Note to convert
     * @return NoteDto
     */
    NoteDto toNoteDto(Note note);
}
