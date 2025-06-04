package com.healthcare.patientmanagementapi.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.model.Patient;
import com.healthcare.patientmanagementapi.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO response = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.status(201).body(response);
    }


    //endpoint to get all patients
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size){
        List<PatientResponseDTO> patients = patientService.getAllPatients(page,size);
        return ResponseEntity.ok(patients);
    }

    // endpoint to get patient by ID
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('RECEPTIONIST')")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        PatientResponseDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    //update an existing patient
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id,
                                                            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO updated = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok(updated);
}
    //delete a patient details
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deletePatientById(@PathVariable Long id){
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsSorted(
            @RequestParam(defaultValue = "age") String sortBy) {
        List<PatientResponseDTO> sortedPatients = patientService.getAllPatientsSorted(sortBy);
        return ResponseEntity.ok(sortedPatients);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PatientResponseDTO>> filterPatients(

            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String diagnosis)
    {

        List<PatientResponseDTO> filtered = patientService.filterPatients(age, gender, diagnosis);
        return ResponseEntity.ok(filtered);
    }
    // PATCH: Update specific fields of a patient by ID
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatientPartially(@PathVariable Long id,
                                                                     @RequestBody PatientRequestDTO partialDTO) {
        PatientResponseDTO updated = patientService.updatePatientPartially(id, partialDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/filter-paged")
    public  ResponseEntity<List<PatientResponseDTO>> filterPatientsPaged(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String diagnosis,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PatientResponseDTO> filteredPage = patientService.filterPatientsPaged(age, gender, diagnosis, pageable);
        return ResponseEntity.ok(filteredPage.getContent());
    }





}
