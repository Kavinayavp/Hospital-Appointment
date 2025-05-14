package com.cts.project.feignclient;

import com.cts.project.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "DOCTORSCHEDULINGANDMANAGEMENTMODULE", url = "http://localhost:8083/doctors")
public interface DoctorClient {

    @GetMapping("/availability/{specialization}")
    List<DoctorDTO> getDoctorAvailability(@PathVariable String specialization);
}
