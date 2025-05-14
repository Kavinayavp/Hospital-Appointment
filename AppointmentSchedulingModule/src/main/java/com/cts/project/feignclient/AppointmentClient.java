package com.cts.project.feignclient;

import com.cts.project.dto.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "APPOINTMENTSCHEDULINGMODULE", url = "http://localhost:8082/appointments")
public interface AppointmentClient {

    @GetMapping("/getappointmentbyid/{id}")
    AppointmentDTO getAppointmentById(@PathVariable Long id);
}
