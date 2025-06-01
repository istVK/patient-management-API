package com.healthcare.patientmanagementapi.service;

import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.model.Patient;

import java.util.List;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);

    List<PatientResponseDTO> getAllPatients();

    PatientResponseDTO getPatientById(Long id);

    PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO);

    PatientResponseDTO updatePatientPartially(Long id, PatientRequestDTO patientDetails);

    void deletePatientById(Long id);
}
