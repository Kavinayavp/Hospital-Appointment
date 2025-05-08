package com.cts.project;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.model.Appointment;
import com.cts.project.repository.AppointmentRepository;
import com.cts.project.service.AppointmentServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.Collections;
 
import static org.junit.jupiter.api.Assertions.*;

 
import static org.mockito.Mockito.*;

public class AppointmentSchedulingModuleApplicationTests {
	 
    private final AppointmentRepository repository = mock(AppointmentRepository.class);
    private final AppointmentServiceImpl service = new AppointmentServiceImpl(repository);
 
    @Test
    void testBookAppointmentWhenDoctorIsAvailable() {
        AppointmentDTO dto = AppointmentDTO.builder()
                .patientId(1L)
                .doctorId(2L)
                .appointmentDate("2025-05-08")
                .appointmentTime("10:00")
                .status("Scheduled")
                .build();
 
        when(repository.findByDoctorIdAndAppointmentDateAndAppointmentTime(2L, "2025-05-08", "10:00"))
                .thenReturn(Collections.emptyList());
 
        Appointment entity = Appointment.builder()
                .appointmentId(1L)
                .patientId(1L)
                .doctorId(2L)
                .appointmentDate("2025-05-08")
                .appointmentTime("10:00")
                .status("Scheduled")
                .build();
 
        when(repository.save(any())).thenReturn(entity);
 
        AppointmentDTO saved = service.bookAppointment(dto);
        assertEquals("Scheduled", saved.getStatus());
    }
 
    @Test
    void testBookAppointmentWhenDoctorIsNotAvailable() {
        AppointmentDTO dto = AppointmentDTO.builder()
                .patientId(1L)
                .doctorId(2L)
                .appointmentDate("2025-05-08")
                .appointmentTime("10:00")
                .status("Scheduled")
                .build();
 
        Appointment existingAppointment = Appointment.builder()
                .appointmentId(1L)
                .patientId(3L)
                .doctorId(2L)
                .appointmentDate("2025-05-08")
                .appointmentTime("10:00")
                .status("Scheduled")
                .build();
 
        when(repository.findByDoctorIdAndAppointmentDateAndAppointmentTime(2L, "2025-05-08", "10:00"))
                .thenReturn(Collections.singletonList(existingAppointment));
 
        assertThrows(IllegalArgumentException.class, () -> service.bookAppointment(dto));
    }
}
 