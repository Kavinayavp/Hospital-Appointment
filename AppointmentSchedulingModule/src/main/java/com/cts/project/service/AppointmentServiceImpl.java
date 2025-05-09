package com.cts.project.service;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.dto.DoctorResponseDTO;
import com.cts.project.dto.PatientResponseDTO;
import com.cts.project.exception.AppointmentNotFoundException;
import com.cts.project.feignclient.DoctorClient;
import com.cts.project.feignclient.PatientClient;
import com.cts.project.model.Appointment;
import com.cts.project.repository.AppointmentRepository;
import java.time.LocalDate;
import java.time.DayOfWeek;
import feign.FeignException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private AppointmentRepository appointmentRepository;

	private PatientClient patientClient;
	
	private DoctorClient doctorClient;
	

	
	@Override
	public String bookAppointment(AppointmentDTO dto) {
	    try {
	        // Fetch doctor details using Feign Client
	        DoctorResponseDTO doctor = doctorClient.getDoctorById(dto.getDoctorId());
	        if (doctor == null) {
	            throw new AppointmentNotFoundException("Doctor not found with ID: " + dto.getDoctorId());
	        }

	        // Validate doctor's availability
	        String appointmentDay = LocalDate.parse(dto.getAppointmentDate()).getDayOfWeek().name(); // e.g., MONDAY

	        if (!doctor.getAvailableDays().stream().map(String::toUpperCase).toList().contains(appointmentDay)) {
	            return "Doctor is not available on " + appointmentDay;
	        }

	        // Proceed with booking
	        Appointment appointment = new Appointment();
	        appointment.setDoctorId(dto.getDoctorId());
	        appointment.setPatientId(dto.getPatientId());
	        appointment.setAppointmentDate(dto.getAppointmentDate());
	        appointment.setAppointmentTime(dto.getAppointmentTime());
	        appointment.setStatus("BOOKED");

	        appointmentRepository.save(appointment);
	        return "Appointment booked successfully";
	    } catch (FeignException.NotFound e) {
	        return "Doctor not found with ID: " + dto.getDoctorId();
	    } catch (Exception e) {
	        return "Error fetching doctor details: " + e.getMessage();
	    }
	}



	@Override
	public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));

		boolean rescheduled = !appointment.getAppointmentDate().equals(dto.getAppointmentDate())
				|| !appointment.getAppointmentTime().equals(dto.getAppointmentTime());

		appointment.setAppointmentDate(dto.getAppointmentDate());
		appointment.setAppointmentTime(dto.getAppointmentTime());

		if ("CANCELLED".equalsIgnoreCase(dto.getStatus())) {
		    appointment.setStatus("CANCELLED");
		} else if (rescheduled) {
		    appointment.setStatus("RESCHEDULED");
		} else {
		    appointment.setStatus("BOOKED");
		}


		Appointment updated = appointmentRepository.save(appointment);
		return mapToDTO(updated);
	}

	@Override
	public AppointmentDTO getAppointmentById(Long appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));
		return mapToDTO(appointment);
	}

	@Override
	public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
		List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
		return appointments.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteAppointment(Long appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));
		appointmentRepository.delete(appointment);
	}

	private AppointmentDTO mapToDTO(Appointment appointment) {
		AppointmentDTO dto = new AppointmentDTO();
		dto.setAppointmentId(appointment.getAppointmentId());
		dto.setDoctorId(appointment.getDoctorId());
		dto.setPatientId(appointment.getPatientId());
		dto.setAppointmentDate(appointment.getAppointmentDate());
		dto.setAppointmentTime(appointment.getAppointmentTime());
		dto.setStatus(appointment.getStatus());
		return dto;
	}

}
