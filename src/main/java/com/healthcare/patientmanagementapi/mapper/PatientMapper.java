package com.healthcare.patientmanagementapi.mapper;

import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.model.Patient;

public class PatientMapper {

    // Convert Request DTO to Patient Entity
    public static Patient maptoEntity(PatientRequestDTO dto) {
        return Patient.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .age(dto.getAge())
                .gender(dto.getGender())
                .diagnosis(dto.getDiagnosis())
                .address(dto.getAddress())
                .build();
    }

    // Convert Patient Entity to Response DTO
    public static PatientResponseDTO maptoDTO(Patient patient) {
        return PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .age(patient.getAge())
                .gender(patient.getGender())
                .diagnosis(patient.getDiagnosis())
                .address(patient.getAddress())
                .build();
    }
}
