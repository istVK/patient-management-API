package com.healthcare.patientmanagementapi.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient")
public class patientController {
    @GetMapping("/ping")
    public String ping(){
        return "Patient Service is Active";
    }
}
