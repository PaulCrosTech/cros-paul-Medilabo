package com.medilabo.ms_patient.mapper;

import com.medilabo.ms_patient.dto.PatientDto;
import com.medilabo.ms_patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between PatientDto and Patient entity.
 */
@Mapper(componentModel = "spring")
public interface PatientMapper {

    /**
     * Converts a PatientDto to a Patient entity.
     *
     * @param patientDto the PatientDto to convert
     * @return the converted Patient entity
     */
    @Mapping(target = "patientId", ignore = true)
    Patient toPatient(PatientDto patientDto);

    /**
     * Converts a Patient entity to a PatientDto.
     *
     * @param patient the Patient entity to convert
     * @return the converted PatientDto
     */
    PatientDto toPatientDto(Patient patient);

}
