package com.healthcare.patientmanagementapi.service;

import com.healthcare.patientmanagementapi.model.Patient;

import java.util.List;

public interface PatientService {
    Patient createPatient (Patient patient);
    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
    Patient updatePatient(Long id, Patient patient);
    Patient updatePatientPartially(Long id, Patient patientDetails);
    void deletePatientById(Long id);
}
