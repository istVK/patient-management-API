package com.healthcare.patientmanagementapi.repository;


import com.healthcare.patientmanagementapi.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p " +
            "WHERE (:age IS NULL OR p.age = :age) " +
            "AND (:gender IS NULL OR p.gender = :gender) " +
            "AND (:diagnosis IS NULL OR p.diagnosis LIKE %:diagnosis%)")
    List<Patient> filterPatients(@Param("age") Integer age,
                                 @Param("gender") String gender,
                                 @Param("diagnosis") String diagnosis);

    @Query("SELECT p FROM Patient p " +
            "WHERE (:age IS NULL OR p.age = :age) " +
            "AND (:gender IS NULL OR LOWER(p.gender) = LOWER(:gender)) " +
            "AND (:diagnosis IS NULL OR LOWER(p.diagnosis) LIKE LOWER(CONCAT('%', :diagnosis, '%')))")
    Page<Patient> findByFilters(
            @Param("age") Integer age,
            @Param("gender") String gender,
            @Param("diagnosis") String diagnosis,
            Pageable pageable
    );
}

