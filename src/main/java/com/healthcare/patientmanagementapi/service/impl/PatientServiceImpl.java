package com.healthcare.patientmanagementapi.service.impl;




import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.exception.ResourceNotFoundException;
import com.healthcare.patientmanagementapi.mapper.PatientMapper;
import com.healthcare.patientmanagementapi.model.Patient;
import com.healthcare.patientmanagementapi.repository.PatientRepository;
import com.healthcare.patientmanagementapi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class PatientServiceImpl implements PatientService {


    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;


    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        logger.info("Creating a new patient: {}", patientRequestDTO.getName());
        Patient newPatient = PatientMapper.maptoEntity(patientRequestDTO);
        Patient savedPatient = patientRepository.save(newPatient);
        logger.debug("Saved patient details: {}", savedPatient);
        return PatientMapper.maptoDTO(savedPatient);
    }

    @Override
    public List<PatientResponseDTO> getAllPatients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Retrieving all patients");
        Page<Patient> patientPage = patientRepository.findAll(pageable);
        logger.debug("Found {} patients", size);
        return patientPage.stream()
                .map(PatientMapper::maptoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        logger.info("Fetching patient with ID: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
        return PatientMapper.maptoDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        logger.info("Updating patient with ID: {}", id);
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));

        existingPatient.setName(patientRequestDTO.getName());
        existingPatient.setSurname(patientRequestDTO.getSurname());
        existingPatient.setEmail(patientRequestDTO.getEmail());
        existingPatient.setAge(patientRequestDTO.getAge());
        existingPatient.setGender(patientRequestDTO.getGender());
        existingPatient.setDiagnosis(patientRequestDTO.getDiagnosis());
        existingPatient.setAddress(patientRequestDTO.getAddress());

        Patient updatedPatient = patientRepository.save(existingPatient);
        return PatientMapper.maptoDTO(updatedPatient);
    }

    @Override
    public void deletePatientById(Long id) {
        logger.info("Deleting patient with ID: {}", id);
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id " + id);
        }
        patientRepository.deleteById(id);
    }

    @Override
    public PatientResponseDTO updatePatientPartially(Long id, PatientRequestDTO  patientDetails) {
        logger.info("Partially updating patient with ID: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));

        if (patientDetails.getName() != null) patient.setName(patientDetails.getName());
        if (patientDetails.getSurname() != null) patient.setSurname(patientDetails.getSurname());
        if (patientDetails.getEmail() != null) patient.setEmail(patientDetails.getEmail());
        if (patientDetails.getAge() != null) patient.setAge(patientDetails.getAge());
        if (patientDetails.getGender() != null) patient.setGender(patientDetails.getGender());
        if (patientDetails.getDiagnosis() != null) patient.setDiagnosis(patientDetails.getDiagnosis());
        if (patientDetails.getAddress() != null) patient.setAddress(patientDetails.getAddress());

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.maptoDTO(updatedPatient);
    }



    @Override
    public List<PatientResponseDTO> getAllPatientsSorted(String sortBy) {
        logger.info("Retrieving all patients sorted: {}", sortBy);
        Sort sort =Sort.by(Sort.Direction.ASC,sortBy);
        List<Patient> sortedPatients  = patientRepository.findAll(sort);
        return sortedPatients.stream().map(PatientMapper::maptoDTO).collect(Collectors.toList());
    }

    @Override
    public List<PatientResponseDTO> filterPatients(Integer age, String gender, String diagnosis) {
        List<Patient> filtered = patientRepository.filterPatients(age, gender, diagnosis);
        return filtered.stream()
                .map(PatientMapper::maptoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PatientResponseDTO> filterPatientsPaged(Integer age, String gender, String diagnosis, Pageable pageable) {
        Page<Patient> filteredPage = patientRepository.findByFilters(age, gender, diagnosis, pageable);
        return filteredPage.map(PatientMapper::maptoDTO);
    }






    // Convert DTO to Entity && Convert Entity to DTO will Be Handled in Utility Mapper



}
