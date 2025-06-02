package com.healthcare.patientmanagementapi.service;

import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);

    List<PatientResponseDTO> getAllPatients(int page , int size);

    PatientResponseDTO getPatientById(Long id);

    PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO);

    PatientResponseDTO updatePatientPartially(Long id, PatientRequestDTO patientDetails);

    void deletePatientById(Long id);

    List<PatientResponseDTO> getAllPatientsSorted(String sortBy);

    List<PatientResponseDTO> filterPatients(Integer age, String gender, String diagnosis);

    Page<PatientResponseDTO> filterPatientsPaged(Integer age, String gender, String diagnosis, Pageable pageable);
}
