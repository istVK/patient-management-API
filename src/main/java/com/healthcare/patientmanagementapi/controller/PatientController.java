package com.healthcare.patientmanagementapi.controller;



import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.model.Patient;
import com.healthcare.patientmanagementapi.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("Patient Service is Active");
    }


    //Endpoint to create a new patient
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO response = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.status(201).body(response);
    }


    //endpoint to get all patients
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients(){
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    // endpoint to get patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        PatientResponseDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    //update an existing patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id,
                                                            @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO updated = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok(updated);
}
    //delete a patient details

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deletePatientById(@PathVariable Long id){
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }


    // PATCH: Update specific fields of a patient by ID
    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatientPartially(@PathVariable Long id,
                                                                     @RequestBody PatientRequestDTO partialDTO) {
        PatientResponseDTO updated = patientService.updatePatientPartially(id, partialDTO);
        return ResponseEntity.ok(updated);
    }
}
