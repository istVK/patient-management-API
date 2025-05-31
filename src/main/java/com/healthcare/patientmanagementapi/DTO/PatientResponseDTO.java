package com.healthcare.patientmanagementapi.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private Integer age;
    private String gender;
    private String diagnosis;
    private String address;
}
