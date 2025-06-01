package com.healthcare.patientmanagementapi.service.impl;




import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.exception.ResourceNotFoundException;
import com.healthcare.patientmanagementapi.model.Patient;
import com.healthcare.patientmanagementapi.repository.PatientRepository;
import com.healthcare.patientmanagementapi.service.PatientService;
import lombok.RequiredArgsConstructor;
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
    public PatientResponseDTO createPatient(Patient patient){
        logger.info("Creating a new patient: {}", patient.getName());
        Patient savedPatient = patientRepository.save(patient);
        logger.debug("Saved patient details: {}", savedPatient);
        return maptoDTO(savedPatient);
    }

    @Override
    public List<PatientResponseDTO> getAllPatients(){
        logger.info("Getting information about all patients");
        List<Patient> patients = patientRepository.findAll();
        logger.debug("Total Patients Found:{}", patients.size());
        return patients.stream().map(this::maptoDTO).collect(Collectors.toList());
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        logger.info("Fetching patient with id {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->  {logger.error("Patient not found with id {}", id);
                return new ResourceNotFoundException("Patient not found with id " + id);
                });

        logger.debug("Patient details: {}", patient);
        return maptoDTO(patient);
    }

    @Override
    public PatientResponseDTO  updatePatient(Long id, Patient patient){
        logger.info("Updating patient with ID: {}", id);
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Patient not found with id " + id);
                });
        existingPatient.setName(patient.getName());
        existingPatient.setSurname(patient.getSurname());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setAge(patient.getAge());
        existingPatient.setGender(patient.getGender());
        existingPatient.setDiagnosis(patient.getDiagnosis());
        existingPatient.setAddress(patient.getAddress());

        Patient saved = patientRepository.save(existingPatient);
        logger.debug("Updated patient details: {}", saved);
        return maptoDTO(saved);
    }

    @Override
    public void deletePatientById(Long id){
        logger.info("Attempting to delete patient with ID: {}", id);
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found for deletion with ID: {}", id);
                    return new ResourceNotFoundException("Patient not found with id " + id);
                });
        patientRepository.delete(existingPatient);
        logger.info("Patient deleted successfully with ID: {}", id);
    }


    @Override
    public PatientResponseDTO updatePatientPartially(Long id, Patient patientDetails) {

        logger.info("Performing partial update for patient with ID: {}", id);
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() ->{ logger.error("Patient not found for partial update with ID: {}", id);
                    return new ResourceNotFoundException("Patient not found with id: " + id);
                });

        // Only update the fields that are not null in patientDetails
        if (patientDetails.getName() != null) {
            existingPatient.setName(patientDetails.getName());
        }
        if (patientDetails.getSurname() != null) {
            existingPatient.setSurname(patientDetails.getSurname());
        }
        if (patientDetails.getEmail() != null) {
            existingPatient.setEmail(patientDetails.getEmail());
        }
        if (patientDetails.getAge() != null) {
            existingPatient.setAge(patientDetails.getAge());
        }
        if (patientDetails.getGender() != null) {
            existingPatient.setGender(patientDetails.getGender());
        }
        if (patientDetails.getDiagnosis() != null) {
            existingPatient.setDiagnosis(patientDetails.getDiagnosis());
        }
        if (patientDetails.getAddress() != null) {
            existingPatient.setAddress(patientDetails.getAddress());
        }

        // Save updated patient
        Patient savedPatient = patientRepository.save(existingPatient);
        logger.debug("Partially updated patient: {}", savedPatient);
        // Map to DTO and return
        return maptoDTO(savedPatient);
    }



    // Convert DTO to Entity
    private Patient mapToEntity(PatientRequestDTO dto){
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

    // Convert Entity to DTO

    private PatientResponseDTO maptoDTO(Patient patient){
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
