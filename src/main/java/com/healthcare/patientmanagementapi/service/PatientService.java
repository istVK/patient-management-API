package com.healthcare.patientmanagementapi.service;

import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.model.Patient;

import java.util.List;

public interface PatientService {
    PatientResponseDTO createPatient (Patient patient);
    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO getPatientById(Long id);
    PatientResponseDTO updatePatient(Long id, Patient patient);
    PatientResponseDTO updatePatientPartially(Long id, Patient patientDetails);
    void deletePatientById(Long id);
}
