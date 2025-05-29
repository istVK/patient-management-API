package com.healthcare.patientmanagementapi.repository;


import com.healthcare.patientmanagementapi.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
}
