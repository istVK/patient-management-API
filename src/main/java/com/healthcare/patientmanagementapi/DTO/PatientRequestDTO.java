package com.healthcare.patientmanagementapi.DTO;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PatientRequestDTO {

    @NotBlank(message = "Name is Mandatory")
    private String name;

    @NotBlank(message = "Surname is Mandatory")
    private String surname;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotNull(message= "Age is Required")
    @Min(value = 0, message="Age must be positive")
    private Integer age;

    @Pattern(
            regexp = "(?i)^(male|female|other)$",
            message = "Gender must be Male, Female, or Other"
    )
    @NotBlank(message = "Gender is Required")
    private String gender;


    @NotBlank(message = "Diagnosis is Required")
    private String diagnosis;

    @NotBlank(message ="Address is Required")
    private String address;

}
