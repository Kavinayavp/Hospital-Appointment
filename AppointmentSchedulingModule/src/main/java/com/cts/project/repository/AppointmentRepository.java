package com.cts.project.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.project.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
   List<Appointment> findByPatientId(Long patientId);
   List<Appointment> findByDoctorIdAndAppointmentDateAndAppointmentTime(Long doctorId, String appointmentDate, String appointmentTime);
}
