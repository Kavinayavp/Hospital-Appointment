package com.cts.project.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.project.model.Appointment;

@FeignClient(name = "APPOINTMENTSCHEDULINGMODULE", url="http://localhost:8082/appointments")
public interface AppointmentClient {

    // ✅ Fetch appointment details by doctorId and patientId
    @GetMapping("/find/{doctorId}/{patientId}")
    Appointment getAppointmentByDoctorIdAndPatientId(@PathVariable Long doctorId, @PathVariable Long patientId);
}
