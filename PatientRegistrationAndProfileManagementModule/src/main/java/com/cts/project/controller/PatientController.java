package com.cts.project.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.project.dto.PatientDTO;
import com.cts.project.service.PatientService;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("patients")
public class PatientController {
 
    @Autowired
    private PatientService patientService;
 
    @PostMapping("/save")
    public PatientDTO savePatient(@Valid @RequestBody PatientDTO dto) {
        return patientService.savePatient(dto);
        
    }
 
    @PutMapping("/update/{id}")
    public PatientDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO dto) {
        return patientService.updatePatient(id, dto);
    }
 
    @GetMapping("/getallpatients")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }
 
    @GetMapping("/getpatientbyid/{id}")
    public PatientDTO getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }
 
    @DeleteMapping("/deletepatient/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
  
}