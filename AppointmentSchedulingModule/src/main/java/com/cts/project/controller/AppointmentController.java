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
         appointmentService.bookAppointment(dto);
         return "Appointment booked successfully";
    }
 
    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @RequestBody AppointmentDTO dto) {
        appointmentService.updateAppointment(id, dto);
        return "Appointment updated successfully";
    }
 
    @GetMapping("/getappointmentbyid/{id}")
    public AppointmentDTO getById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }
 
    @GetMapping("/getappointmentbypatientid/{patientId}")
    public List<AppointmentDTO> getByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }
    @DeleteMapping("/deleteappointment/{id}")
    public String delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "Appointment deleted successfully";
    }
}