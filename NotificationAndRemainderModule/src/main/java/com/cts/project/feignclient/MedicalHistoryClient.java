package com.cts.project.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.cts.project.dto.MedicalHistoryResponseDTO;

@FeignClient(name = "MEDICALHISTORYMANAGEMENTMODULE", url = "http://localhost:8084/history")
public interface MedicalHistoryClient {
    @GetMapping("/gethistorybypatientid/{patientId}")
    List<MedicalHistoryResponseDTO> getHistoryByPatientId(@PathVariable Long patientId);
}