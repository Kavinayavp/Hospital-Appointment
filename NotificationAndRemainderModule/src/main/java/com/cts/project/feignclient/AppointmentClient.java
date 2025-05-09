package com.cts.project.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cts.project.dto.AppointmentResponseDTO;

@FeignClient(name = "APPOINTMENTSCHEDULINGMODULE", url = "http://localhost:8082/appointments")
public interface AppointmentClient {
    @GetMapping("/getappointmentbyid/{appointmentId}")
    AppointmentResponseDTO getAppointmentById(@PathVariable Long appointmentId);
}
