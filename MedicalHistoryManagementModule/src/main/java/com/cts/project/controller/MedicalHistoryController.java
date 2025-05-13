package com.cts.project.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.service.MedicalHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class MedicalHistoryController {

    @Autowired
    private final MedicalHistoryService medicalHistoryService;

    /** ✅ Save medical history without appointment validation */
    @PostMapping("/save")
    public String save(@Valid @RequestBody MedicalHistoryDTO dto) {
        medicalHistoryService.saveMedicalHistory(dto);
        return "Medical history saved successfully.";
    }

    /** ✅ Update medical history just using historyId */
    @PutMapping("/update/{historyId}")
    public String update(@PathVariable Long historyId, @Valid @RequestBody MedicalHistoryDTO dto) {
        medicalHistoryService.updateMedicalHistory(historyId, dto);
        return "Medical history updated successfully for history Id "+historyId;
    }

    /** ✅ Get all medical histories */
    @GetMapping("/getallhistory")
    public List<MedicalHistoryDTO> getAllMedicalHistory() {
        return medicalHistoryService.getAllMedicalHistory();
    }

    /** ✅ Delete medical history by ID */
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        medicalHistoryService.deleteMedicalHistory(id);
        return "Medical history deleted successfully.";
    }

    /** ✅ Fetch medical history by patient ID */
    @GetMapping("/gethistorybypatientid/{patientId}")
    public List<MedicalHistoryDTO> getHistoryByPatientId(@PathVariable Long patientId) {
        return medicalHistoryService.getMedicalHistoryByPatientId(patientId);
    }
}
