package com.cts.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
 
@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {
 
    private AppointmentService appointmentService;
 
    @PostMapping("/book")
    public String book(@Valid @RequestBody AppointmentDTO dto) {
        return appointmentService.bookAppointment(dto);
    }
 
   
    @PutMapping("/update/{appointmentId}/{patientId}")
    public String updateAppointment(Long appointmentId, Long patientId, AppointmentDTO dto){
        return appointmentService.updateAppointment(appointmentId, patientId, dto);
    }

    @DeleteMapping("/deleteappointment/{appointmentId}/{patientId}")
    public String delete(@PathVariable Long appointmentId, @PathVariable Long patientId) {
        return appointmentService.deleteAppointment(appointmentId, patientId);
    }

 
    @GetMapping("/getappointmentbyid/{appointmentid}")
    public AppointmentDTO getById(@PathVariable Long appointmentid) {
        return appointmentService.getAppointmentById(appointmentid);
    }
 
    @GetMapping("/getappointmentbypatientid/{patientId}")
    public List<AppointmentDTO> getByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }
  
}