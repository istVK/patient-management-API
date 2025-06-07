package com.healthcare.patientmanagementapi.service;

import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;

import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.exception.ResourceNotFoundException;
import com.healthcare.patientmanagementapi.model.Patient;
import com.healthcare.patientmanagementapi.repository.PatientRepository;
import com.healthcare.patientmanagementapi.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private PatientRequestDTO patientRequestDTO;

    @BeforeEach

    void setUp() {
        patient = Patient.builder()
                .id(1L)  // long literal because id is of type Long
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .age(30)
                .gender("Male")
                .diagnosis("Flu")
                .address("123 Main Street")
                .build();

        patientRequestDTO = PatientRequestDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .age(30)
                .gender("Male")
                .diagnosis("Flu")
                .address("123 Main Street")
                .build();
    }

    @Test
    public void testCreatePatient_Success() {

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        var response = patientService.createPatient(patientRequestDTO);

        assertNotNull(response);
        assertEquals(patient.getName(),response.getName());
        assertEquals(patient.getEmail(),response.getEmail());

        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testGetPatientByID_Success() {

        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        PatientResponseDTO result = patientService.getPatientById(patientId);

        assertNotNull(result);
        assertEquals(patient.getName(), result.getName());
        assertEquals(patient.getSurname(), result.getSurname());
        assertEquals(patient.getEmail(), result.getEmail());
        assertEquals(patient.getAge(), result.getAge());
        assertEquals(patient.getGender(), result.getGender());
        assertEquals(patient.getDiagnosis(), result.getDiagnosis());
        assertEquals(patient.getAddress(), result.getAddress());

        verify(patientRepository, times(1)).findById(patientId);

    }

    @Test
    public void testGetPatientById_NotFound() {
        Long patientId = 99L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            patientService.getPatientById(patientId);
        });

        assertEquals("Patient not found with id 99", exception.getMessage());

        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    public void testUpdatePatient_Success() {
        Long patientId = 1L;

        // Existing patient in DB
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Expected updated patient object
        Patient updatedPatient = Patient.builder()
                .id(patientId)
                .name("UpdatedName")
                .surname("UpdatedSurname")
                .email("updated@example.com")
                .age(35)
                .gender("Other")
                .diagnosis("Updated Diagnosis")
                .address("456 Updated Street")
                .build();

        // Incoming updated data from DTO
        PatientRequestDTO updateDTO = PatientRequestDTO.builder()
                .name("UpdatedName")
                .surname("UpdatedSurname")
                .email("updated@example.com")
                .age(35)
                .gender("Other")
                .diagnosis("Updated Diagnosis")
                .address("456 Updated Street")
                .build();

        // What repository should return when saving
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        // Call method under test
        PatientResponseDTO result = patientService.updatePatient(patientId, updateDTO);

        //  Assertions
        assertNotNull(result);
        assertEquals("UpdatedName", result.getName());
        assertEquals("UpdatedSurname", result.getSurname());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals(35, result.getAge());
        assertEquals("Other", result.getGender());
        assertEquals("Updated Diagnosis", result.getDiagnosis());
        assertEquals("456 Updated Street", result.getAddress());

        // Verify repository interaction
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testDeletePatient_Success() {
        when(patientRepository.existsById(2L)).thenReturn(true);

        // call delete method
        patientService.deletePatientById(2L);

        verify(patientRepository, times(1)).existsById(2L);
        verify(patientRepository, never()).delete(any(Patient.class));
    }

    @Test
    public void testDeletePatient_NotFound() {
        Long patientId = 99L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        PatientRequestDTO updateDTO = PatientRequestDTO.builder()
                .name("Ghost")
                .surname("User")
                .email("ghost@example.com")
                .age(40)
                .gender("Other")
                .diagnosis("N/A")
                .address("Nowhere")
                .build();

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> patientService.updatePatient(patientId, updateDTO)
        );

        assertEquals("Patient not found with id 99", exception.getMessage());

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).save(any(Patient.class));
    }
    @Test
    public void testGetAllPatients() {
        int page = 0;
        int size = 5;

        Pageable pageable = PageRequest.of(page, size);

        List<Patient> patients = Arrays.asList(patient);
        Page<Patient> patientPage = new PageImpl<>(patients, pageable, patients.size());

        when(patientRepository.findAll(pageable)).thenReturn(patientPage);

        List<PatientResponseDTO> result = patientService.getAllPatients(page, size);

        assertNotNull(result);
        assertEquals(1, result.size()); // You can update count if you add more patients

        assertEquals(patient.getName(), result.get(0).getName());
        assertEquals(patient.getEmail(), result.get(0).getEmail());

        verify(patientRepository, times(1)).findAll(pageable);
    }



}
