package com.cts.project.controller;


import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.service.MedicalHistoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@AllArgsConstructor
public class MedicalHistoryController {

	@Autowired
   private MedicalHistoryService medicalHistoryService;

   @PostMapping("/save")
   public String save(@Valid @RequestBody MedicalHistoryDTO dto) {
        medicalHistoryService.saveMedicalHistory(dto);
        return "Medical history saved successfully";
   }

   @PutMapping("/update/{id}")
   public String update(@PathVariable Long id, @Valid @RequestBody MedicalHistoryDTO dto) {
       medicalHistoryService.updateMedicalHistory(id, dto);
       return "Medical history updated successfully";
   }

   @GetMapping("/getallhistory")
   public List<MedicalHistoryDTO> getAllMedicalHistory() {
       return medicalHistoryService.getAllMedicalHistory();
   }
   @GetMapping("/gethistorybypatientid/{patientId}")
   public List<MedicalHistoryDTO> getByPatientId(@PathVariable Long patientId) {
       return medicalHistoryService.getMedicalHistoryByPatientId(patientId);
   }

   @DeleteMapping("/delete/{id}")
   public String delete(@PathVariable Long id) {
       medicalHistoryService.deleteMedicalHistory(id);
       return " Medical History Deleted Successfully";
   }
}
