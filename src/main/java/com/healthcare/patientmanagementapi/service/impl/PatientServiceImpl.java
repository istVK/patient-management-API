package com.healthcare.patientmanagementapi.service.impl;



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
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElse(null);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient){
        patient.setId(id);
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatientById(Long id){
        patientRepository.deleteById(id);
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
}
