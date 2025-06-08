package com.healthcare.patientmanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.healthcare.patientmanagementapi.DTO.PatientRequestDTO;
import com.healthcare.patientmanagementapi.DTO.PatientResponseDTO;
import com.healthcare.patientmanagementapi.service.PatientService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;




@WebMvcTest(PatientController.class)
public class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService ;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetAllPatients() throws Exception {

        PatientResponseDTO patient1 = new PatientResponseDTO();
        patient1.setId(1L);
        patient1.setName("John");
        patient1.setSurname("Doe");
        patient1.setEmail("john@example.com");
        patient1.setAge(30);
        patient1.setGender("Male");
        patient1.setDiagnosis("Flu");
        patient1.setAddress("123 Main St");

        PatientResponseDTO patient2 = new PatientResponseDTO();
        patient2.setId(2L);
        patient2.setName("Jane");
        patient2.setSurname("Smith");
        patient2.setEmail("jane@example.com");
        patient2.setAge(25);
        patient2.setGender("Female");
        patient2.setDiagnosis("Cold");
        patient2.setAddress("456 Second St");

        List<PatientResponseDTO> mockList = Arrays.asList(patient1, patient2);

        when(patientService.getAllPatients(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockList);

        mockMvc.perform(get("/api/patients")
                        .param("page", "0")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"))
                .andExpect(jsonPath("$[1].address").value("456 Second St"));
    }


    @Test
    public void testGetPatientById() throws Exception {
        Long patientId = 1L;

        PatientResponseDTO patient = new PatientResponseDTO();
        patient.setId(patientId);
        patient.setName("John");
        patient.setSurname("Doe");
        patient.setEmail("john@example.com");
        patient.setAge(30);
        patient.setGender("Male");
        patient.setDiagnosis("Flu");
        patient.setAddress("123 Main St");

        when(patientService.getPatientById(patientId)).thenReturn(patient);

        mockMvc.perform(get("/api/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientId))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testCreatePatient() throws Exception {
        PatientRequestDTO newPatientRequest = new PatientRequestDTO();
        newPatientRequest.setName("Alice");
        newPatientRequest.setSurname("Wonder");
        newPatientRequest.setEmail("alice@example.com");
        newPatientRequest.setAge(28);
        newPatientRequest.setGender("Female");
        newPatientRequest.setDiagnosis("Allergy");
        newPatientRequest.setAddress("789 Maple Ave");

        PatientResponseDTO createdPatientResponse = new PatientResponseDTO();
        createdPatientResponse.setId(3L);
        createdPatientResponse.setName(newPatientRequest.getName());
        createdPatientResponse.setSurname(newPatientRequest.getSurname());
        createdPatientResponse.setEmail(newPatientRequest.getEmail());
        createdPatientResponse.setAge(newPatientRequest.getAge());
        createdPatientResponse.setGender(newPatientRequest.getGender());
        createdPatientResponse.setDiagnosis(newPatientRequest.getDiagnosis());
        createdPatientResponse.setAddress(newPatientRequest.getAddress());

        // Mock service call
        when(patientService.createPatient(Mockito.any(PatientRequestDTO.class))).thenReturn(createdPatientResponse);

        // Convert request DTO to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(newPatientRequest);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Wonder"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Long patientId = 1L;

        PatientRequestDTO updateRequest = new PatientRequestDTO();
        updateRequest.setName("John Updated");
        updateRequest.setSurname("Doe Updated");
        updateRequest.setEmail("john.updated@example.com");
        updateRequest.setAge(31);
        updateRequest.setGender("Male");
        updateRequest.setDiagnosis("Recovered");
        updateRequest.setAddress("123 Updated St");

        PatientResponseDTO updatedResponse = new PatientResponseDTO();
        updatedResponse.setId(patientId);
        updatedResponse.setName(updateRequest.getName());
        updatedResponse.setSurname(updateRequest.getSurname());
        updatedResponse.setEmail(updateRequest.getEmail());
        updatedResponse.setAge(updateRequest.getAge());
        updatedResponse.setGender(updateRequest.getGender());
        updatedResponse.setDiagnosis(updateRequest.getDiagnosis());
        updatedResponse.setAddress(updateRequest.getAddress());

        when(patientService.updatePatient(Mockito.eq(patientId), Mockito.any(PatientRequestDTO.class)))
                .thenReturn(updatedResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String updateJson = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/api/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientId))
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.surname").value("Doe Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));
    }

    @Test
    public void testDeletePatient() throws Exception {

        Long patientId = 1L;

        doNothing().when(patientService).deletePatientById(patientId);

        mockMvc.perform(delete("/api/patients/{id}", patientId))
                .andExpect(status().isNoContent());

        // Optional: Verify the service method was called once
        verify(patientService, times(1)).deletePatientById(patientId);
    }




}
