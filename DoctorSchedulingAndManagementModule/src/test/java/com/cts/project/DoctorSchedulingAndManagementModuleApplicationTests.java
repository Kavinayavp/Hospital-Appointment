package com.cts.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.project.controller.DoctorController;
import com.cts.project.dto.DoctorDTO;
import com.cts.project.service.DoctorService;

class DoctorControllerTest {

	@InjectMocks
	private DoctorController doctorController;

	@Mock
	private DoctorService doctorService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // ✅ Initializes Mockito annotations
		mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
	}

	@AfterEach
	void tearDown() {
		// ✅ Cleanup logic if needed (e.g., closing database connections)
	}

	@Test
	void testSaveDoctor() throws Exception {
		DoctorDTO doctorDTO = new DoctorDTO(1L, "Dr. John Doe", "Cardiologist", 10, "9876543210", "doctor@example.com",
				List.of("Monday", "Wednesday"), "10:00 AM - 06:00 PM");

		doNothing().when(doctorService).saveDoctor(any(DoctorDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/doctors/save").contentType(MediaType.APPLICATION_JSON)
				.content("{\"doctorId\":1, \"doctorName\":\"Dr. John Doe\", \"specialization\":\"Cardiologist\", "
						+ "\"experience\":10, \"contactNumber\":\"9876543210\", \"email\":\"doctor@example.com\", "
						+ "\"availableDays\":[\"Monday\", \"Wednesday\"], \"availableTime\":\"10:00 AM - 06:00 PM\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Doctor details saved successfully."));
	}

	@Test
	void testUpdateDoctor() throws Exception {
		DoctorDTO doctorDTO = new DoctorDTO(1L, "Dr. Jane Doe", "Neurologist", 15, "9123456789", "jane.doe@example.com",
				List.of("Tuesday", "Thursday"), "09:00 AM - 05:00 PM");

		doNothing().when(doctorService).updateDoctor(eq(1L), any(DoctorDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.put("/doctors/update/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"doctorId\":1, \"doctorName\":\"Dr. Jane Doe\", \"specialization\":\"Neurologist\", "
						+ "\"experience\":15, \"contactNumber\":\"9123456789\", \"email\":\"jane.doe@example.com\", "
						+ "\"availableDays\":[\"Tuesday\", \"Thursday\"], \"availableTime\":\"09:00 AM - 05:00 PM\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Doctor details updated successfully."));
	}

	@Test
	void testGetAllDoctors() throws Exception {
		List<DoctorDTO> doctors = Arrays.asList(
				new DoctorDTO(1L, "Dr. Smith", "Cardiologist", 20, "8901234567", "smith@example.com",
						List.of("Monday", "Friday"), "08:00 AM - 04:00 PM"),
				new DoctorDTO(2L, "Dr. Adams", "Dermatologist", 12, "8765432109", "adams@example.com",
						List.of("Wednesday", "Saturday"), "10:00 AM - 06:00 PM"));

		when(doctorService.getAllDoctors()).thenReturn(doctors);

		mockMvc.perform(MockMvcRequestBuilders.get("/doctors/getalldoctors"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	void testGetDoctorById() throws Exception {
		DoctorDTO doctorDTO = new DoctorDTO(1L, "Dr. Smith", "Cardiologist", 25, "8112233445", "smith@example.com",
				List.of("Tuesday", "Thursday"), "10:00 AM - 07:00 PM");

		when(doctorService.getDoctorById(1L)).thenReturn(doctorDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/doctors/getdoctorbyid/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.doctorName").value("Dr. Smith"));
	}

	@Test
	void testDeleteDoctor() throws Exception {
		doNothing().when(doctorService).deleteDoctor(1L);

		mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/deletedoctor/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Doctor with ID 1 deleted successfully."));
	}

	@Test
	void testGetAvailability() throws Exception {
		List<DoctorDTO> doctors = Arrays.asList(new DoctorDTO(1L, "Dr. Williams", "Cardiologist", 30, "8223344556",
				"williams@example.com", List.of("Monday", "Wednesday"), "08:30 AM - 03:30 PM"));

		when(doctorService.getDoctorAvailability("Cardiologist")).thenReturn(doctors);

		mockMvc.perform(MockMvcRequestBuilders.get("/doctors/availability/Cardiologist"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].doctorName").value("Dr. Williams"));
	}
}
