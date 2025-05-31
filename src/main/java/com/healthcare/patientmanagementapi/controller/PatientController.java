package com.healthcare.patientmanagementapi.controller;



import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.model.Patient;
import com.healthcare.patientmanagementapi.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {


    private final PatientService patientService;

    //constructor injection for patient services

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    //Active  check Endpoint
    @GetMapping("/ping")
    public String ping(){
        return "Patient Service is Active";
    }


    //Endpoint to create a new patient
    @PostMapping
    public Patient createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = Patient.builder()
                .name(patientRequestDTO.getName())
                .surname(patientRequestDTO.getSurname())
                .email(patientRequestDTO.getEmail())
                .age(patientRequestDTO.getAge())
                .gender(patientRequestDTO.getGender())
                .diagnosis(patientRequestDTO.getDiagnosis())
                .address(patientRequestDTO.getAddress())
                .build();

        return patientService.createPatient(patient);
    }


    //endpoint to get all patients
    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    // endpoint to get patient by ID
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

    //update an existing patient
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient){
        return patientService.updatePatient(id, patient);
    }

    //delete a patient details

    @DeleteMapping("/{id}")
    public void deletePatientById(@PathVariable Long id){
        patientService.deletePatientById(id);
    }

    // PATCH: Update specific fields of a patient by ID
    @PatchMapping("/{id}")
    public Patient updatePatientPartially(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientService.updatePatientPartially(id, patientDetails);
    }
}
