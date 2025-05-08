package com.cts.project;

import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.exception.MedicalHistoryNotFoundException;
import com.cts.project.model.MedicalHistory;
import com.cts.project.repository.MedicalHistoryRepository;
import com.cts.project.service.MedicalHistoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class MedicalHistoryServiceImplTest {
	 
    @Mock
    private MedicalHistoryRepository repository;
 
    @InjectMocks
    private MedicalHistoryServiceImpl service;
 
    private MedicalHistory history;
    private MedicalHistoryDTO dto;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Set up a sample medical history entity and DTO
        history = MedicalHistory.builder()
                .historyId(1L)
                .patientId(1001L)
                .illness("Flu")
                .treatment("Rest and fluids")
                .doctorName("Dr. Sarah Johnson")
                .visitDate("2025-05-01")
                .prescription("Paracetamol")
                .build();
 
        dto = MedicalHistoryDTO.builder()
                .historyId(1L)
                .patientId(1001L)
                .illness("Flu")
                .treatment("Rest and fluids")
                .doctorName("Dr. Sarah Johnson")
                .visitDate("2025-05-01")
                .prescription("Paracetamol")
                .build();
    }
 
    @Test
    void saveHistory_ShouldSaveSuccessfully() {
        when(repository.save(any())).thenReturn(history);
        
        MedicalHistoryDTO result = service.saveMedicalHistory(dto);
        assertEquals(dto.getIllness(), result.getIllness());
        assertEquals(dto.getTreatment(), result.getTreatment());
        assertEquals(dto.getDoctorName(), result.getDoctorName());
        verify(repository, times(1)).save(any());
    }
 
    @Test
    void updateHistory_ShouldUpdateIfExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(history));
        when(repository.save(any())).thenReturn(history);
 
        dto.setIllness("Updated Flu");
        dto.setPrescription("Updated Prescription");
        
        MedicalHistoryDTO result = service.updateMedicalHistory(1L, dto);
 
        assertEquals("Updated Flu", result.getIllness());
        assertEquals("Updated Prescription", result.getPrescription());
        verify(repository).findById(1L);
        verify(repository).save(any());
    }
 
    @Test
    void updateHistory_ShouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MedicalHistoryNotFoundException.class, () -> service.updateMedicalHistory(1L, dto));
    }
 
    @Test
    void getHistoryByPatientId_ShouldReturnIfExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(history));
        MedicalHistoryDTO result = (MedicalHistoryDTO) service.getMedicalHistoryByPatientId(1L);
        assertEquals(history.getIllness(), result.getIllness());
    }
 
    @Test
    void getHistoryById_ShouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MedicalHistoryNotFoundException.class, () -> service.getMedicalHistoryByPatientId(1L));
    }
 
    @Test
    void getHistoriesByPatientId_ShouldReturnList() {
        List<MedicalHistory> list = List.of(history);
        when(repository.findByPatientId(1001L)).thenReturn(list);
        List<MedicalHistoryDTO> result = service.getMedicalHistoryByPatientId(1001L);
        assertEquals(1, result.size());
        assertEquals("Flu", result.get(0).getIllness());
    }
 
    @Test
    void deleteHistory_ShouldDeleteIfExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(history));
        service.deleteMedicalHistory(1L);
        verify(repository).deleteById(1L);
    }
 
    @Test
    void deleteHistory_ShouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MedicalHistoryNotFoundException.class, () -> service.deleteMedicalHistory(1L));
    }
}