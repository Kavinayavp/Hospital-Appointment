package com.cts.project.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.exception.UnauthorizedAccessException;
import com.cts.project.feignclient.AppointmentClient;
import com.cts.project.model.Appointment;
import com.cts.project.service.MedicalHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class MedicalHistoryController {

   @Autowired
   private final MedicalHistoryService medicalHistoryService;

   @Autowired
   private final AppointmentClient appointmentClient; // ✅ Inject Feign Client for doctor-patient validation

   @PostMapping("/save")
   public ResponseEntity<String> save(@Valid @RequestBody MedicalHistoryDTO dto) {
       try {
           // ✅ Validate appointment before saving history
           Appointment appointment = appointmentClient.getAppointmentByDoctorIdAndPatientId(dto.getDoctorId(), dto.getPatientId());

           if (appointment == null) {
               throw new UnauthorizedAccessException("Doctor with ID " + dto.getDoctorId() + " did not attend patient with ID " + dto.getPatientId());
           }

           medicalHistoryService.saveMedicalHistory(dto);
           return ResponseEntity.status(HttpStatus.CREATED).body("Medical history saved successfully.");

       } catch (FeignException.NotFound e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found for Doctor ID " + dto.getDoctorId() + " and Patient ID " + dto.getPatientId());
       } catch (FeignException.ServiceUnavailable e) {
           return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Appointment Service is currently unavailable. Please try again later.");
       }
   }

   @PutMapping("/update/{id}")
   public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody MedicalHistoryDTO dto) {
       try {
           // ✅ Validate appointment before updating history
           Appointment appointment = appointmentClient.getAppointmentByDoctorIdAndPatientId(dto.getDoctorId(), dto.getPatientId());

           if (appointment == null) {
               throw new UnauthorizedAccessException("Doctor with ID " + dto.getDoctorId() + " did not attend patient with ID " + dto.getPatientId());
           }

           medicalHistoryService.updateMedicalHistory(id, dto);
           return ResponseEntity.ok("Medical history updated successfully.");

       } catch (FeignException.NotFound e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found for Doctor ID " + dto.getDoctorId() + " and Patient ID " + dto.getPatientId());
       } catch (FeignException.ServiceUnavailable e) {
           return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Appointment Service is currently unavailable. Please try again later.");
       }
   }

   @GetMapping("/getallhistory")
   public ResponseEntity<List<MedicalHistoryDTO>> getAllMedicalHistory() {
       return ResponseEntity.ok(medicalHistoryService.getAllMedicalHistory());
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<String> delete(@PathVariable Long id) {
       medicalHistoryService.deleteMedicalHistory(id);
       return ResponseEntity.ok("Medical history deleted successfully.");
   }

   @GetMapping("/gethistorybypatientid/{patientId}")
   public ResponseEntity<List<MedicalHistoryDTO>> getHistoryByPatientId(@PathVariable Long patientId) {
       return ResponseEntity.ok(medicalHistoryService.getMedicalHistoryByPatientId(patientId));
   }

   // ✅ Fetch appointment details by doctorId and patientId
   @GetMapping("/find/{doctorId}/{patientId}")
   public ResponseEntity<?> getAppointmentByDoctorIdAndPatientId(@PathVariable Long doctorId, @PathVariable Long patientId) {
       try {
           Appointment appointment = appointmentClient.getAppointmentByDoctorIdAndPatientId(doctorId, patientId);
           return ResponseEntity.ok(appointment);
       } catch (FeignException.NotFound e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No appointment found for Doctor ID " + doctorId + " and Patient ID " + patientId);
       } catch (FeignException.ServiceUnavailable e) {
           return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Appointment Service is unavailable. Please try again later.");
       }
   }

   // ✅ Handle Unauthorized Access Errors Gracefully
   @ExceptionHandler(UnauthorizedAccessException.class)
   public ResponseEntity<String> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
       return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
   }
}
