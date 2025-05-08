package com.cts.project;

import com.cts.project.exception.ResourceNotFoundException;
import com.cts.project.repository.PatientRepository;
import com.cts.project.service.PatientServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
 
import static org.junit.jupiter.api.Assertions.*;
 
@ExtendWith(MockitoExtension.class)
public class PatientRegistrationAndProfileManagementModuleApplicationTests {
 
    @Mock
    private PatientRepository patientRepository;
 
    @InjectMocks
    private PatientServiceImpl patientService;
 
    @Test
    void testDeletePatientSuccess() {
        Long patientId = 1L;
        when(patientRepository.existsById(patientId)).thenReturn(true);
 
        patientService.deletePatient(patientId);
 
        verify(patientRepository, times(1)).deleteById(patientId);
    }
 
    @Test
    void testDeletePatientNotFound() {
        Long patientId = 2L;
        when(patientRepository.existsById(patientId)).thenReturn(false);
 
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            patientService.deletePatient(patientId);
        });
 
        assertEquals("Patient not found with id: 2", exception.getMessage());
        verify(patientRepository, never()).deleteById(anyLong());
    }
}