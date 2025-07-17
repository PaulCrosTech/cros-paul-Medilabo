package com.medilabo.ms_note.mapper;


import com.medilabo.ms_note.dto.NoteCreateDto;
import com.medilabo.ms_note.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper class for converting between Note entities and Note DTOs.
 * This class is used to map data between the entity and DTO layers.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper {

    /**
     * Converts a NoteCreateDto entity to a Note.
     *
     * @param noteCreateDto the NoteCreateDto to convert
     * @return the converted Note entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    Note NoteCreateDtoToNote(NoteCreateDto noteCreateDto);
}
