package com.cts.project.feignclient;

import com.cts.project.dto.DoctorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "DOCTORSCHEDULINGANDMANAGEMENTMODULE", url = "http://localhost:8083/doctors")
public interface DoctorClient {

    @GetMapping("/getdoctorbyid/{id}")
    DoctorResponseDTO getDoctorById(@PathVariable Long id);
}
