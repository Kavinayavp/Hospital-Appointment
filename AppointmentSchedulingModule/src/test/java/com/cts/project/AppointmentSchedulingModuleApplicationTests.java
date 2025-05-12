package com.cts.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.dto.DoctorResponseDTO;
import com.cts.project.dto.PatientResponseDTO;
import com.cts.project.exception.AppointmentNotFoundException;
import com.cts.project.feignclient.DoctorClient;
import com.cts.project.feignclient.PatientClient;
import com.cts.project.model.Appointment;
import com.cts.project.repository.AppointmentRepository;
import com.cts.project.service.AppointmentServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientClient patientClient;

    @Mock
    private DoctorClient doctorClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Appointment getSampleAppointment() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setDoctorId(2L);
        appointment.setDoctorName("Dr. Smith"); // ✅ Added doctor name
        appointment.setPatientId(4L);
        appointment.setPatientName("John Doe"); // ✅ Added patient name
        appointment.setAppointmentDate("2025-05-23");
        appointment.setAppointmentTime("10:00 AM");
        appointment.setStatus("BOOKED");
        return appointment;
    }

    private AppointmentDTO getSampleDTO() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setDoctorId(2L);
        dto.setDoctorName("Dr. Smith"); // ✅ Added doctor name
        dto.setPatientId(3L);
        dto.setPatientName("John Doe"); // ✅ Added patient name
        dto.setAppointmentDate("2025-05-23");
        dto.setAppointmentTime("10:00 AM");
        return dto;
    }

    private DoctorResponseDTO getSampleDoctorResponse() {
        DoctorResponseDTO response = new DoctorResponseDTO();
        response.setDoctorId(2L);
        response.setDoctorName("Dr. Smith");
        response.setAvailableDays(Arrays.asList("MONDAY", "WEDNESDAY", "FRIDAY"));
        return response;
    }

    private PatientResponseDTO getSamplePatientResponse() {
        PatientResponseDTO response = new PatientResponseDTO();
        response.setPatientId(3L);
        response.setPatientName("John Doe");
        return response;
    }

    @Test
    void testBookAppointment_Success() {
        AppointmentDTO dto = getSampleDTO();
        Appointment saved = getSampleAppointment();

        when(doctorClient.getDoctorById(2L)).thenReturn(getSampleDoctorResponse());
        when(patientClient.getPatientById(3L)).thenReturn(getSamplePatientResponse());
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(saved);

        String result = appointmentService.bookAppointment(dto);

        assertNotNull(result);
        assertEquals("Appointment booked successfully!", result);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testBookAppointment_DoctorNotFound() {
        AppointmentDTO dto = getSampleDTO();
        when(doctorClient.getDoctorById(2L)).thenReturn(null);

        assertEquals("Doctor not found with ID: 2", appointmentService.bookAppointment(dto));
    }

    @Test
    void testBookAppointment_PatientNotFound() {
        AppointmentDTO dto = getSampleDTO();
        when(patientClient.getPatientById(3L)).thenReturn(null);

        assertEquals("Doctor or Patient not found.", appointmentService.bookAppointment(dto));
    }

    @Test
    void testGetAppointmentById_Success() {
        Appointment appointment = getSampleAppointment();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        AppointmentDTO result = appointmentService.getAppointmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getAppointmentId());
        assertEquals("Dr. Smith", result.getDoctorName()); // ✅ Validate doctor name
        assertEquals("John Doe", result.getPatientName()); // ✅ Validate patient name
    }

    @Test
    void testGetAppointmentById_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.getAppointmentById(1L));
    }

    @Test
    void testGetAppointmentsByPatientId() {
        List<Appointment> list = Arrays.asList(getSampleAppointment());
        when(appointmentRepository.findByPatientId(3L)).thenReturn(list);

        List<AppointmentDTO> results = appointmentService.getAppointmentsByPatientId(3L);

        assertEquals(1, results.size());
        assertEquals("BOOKED", results.get(0).getStatus());
    }

    @Test
    void testDeleteAppointment_Success() {
        Appointment appointment = getSampleAppointment();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentService.deleteAppointment(1L, 2L);

        verify(appointmentRepository, times(1)).delete(appointment);
    }

    @Test
    void testDeleteAppointment_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.deleteAppointment(1L, 2L));
    }
    @Test
    void testUpdateAppointment_Reschedule() {
        Appointment existing = getSampleAppointment();
        AppointmentDTO updateDTO = getSampleDTO();
        updateDTO.setAppointmentDate("2025-05-26");
        updateDTO.setAppointmentTime("2:00 PM");

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(existing);

        String updatedStatus = appointmentService.updateAppointment(1L, 2L, updateDTO); 

        assertEquals("RESCHEDULED", updatedStatus); 
    }

    }
