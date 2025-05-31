package com.healthcare.patientmanagementapi.service.impl;



import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.exception.ResourceNotFoundException;
import com.healthcare.patientmanagementapi.model.Patient;
import com.healthcare.patientmanagementapi.repository.PatientRepository;
import com.healthcare.patientmanagementapi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient){
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    @Override
    public Patient updatePatient(Long id, Patient patient){
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        existingPatient.setName(patient.getName());
        existingPatient.setSurname(patient.getSurname());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setAge(patient.getAge());
        existingPatient.setGender(patient.getGender());
        existingPatient.setDiagnosis(patient.getDiagnosis());
        existingPatient.setAddress(patient.getAddress());

        return patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatientById(Long id){

        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patientRepository.delete(existingPatient);
    }


    @Override
    public Patient updatePatientPartially(Long id, Patient patientDetails) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();

            // Only update the fields that are not null in patientDetails
            if (patientDetails.getName() != null) {
                patient.setName(patientDetails.getName());
            }
            if (patientDetails.getAge() != null) {
                patient.setAge(patientDetails.getAge());
            }
            if (patientDetails.getGender() != null) {
                patient.setGender(patientDetails.getGender());
            }
            if (patientDetails.getDiagnosis() != null) {
                patient.setDiagnosis(patientDetails.getDiagnosis());
            }
            if (patientDetails.getAddress() != null) {
                patient.setAddress(patientDetails.getAddress());
            }
            if (patientDetails.getEmail() != null) {
                patient.setEmail(patientDetails.getEmail());
            }

            if (patientDetails.getSurname() != null) {
                patient.setSurname(patientDetails.getSurname());
            }


            // Save the partially updated patient record
            return patientRepository.save(patient);
        } else {
            return null; // You can return a custom error or exception if patient not found
        }
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
