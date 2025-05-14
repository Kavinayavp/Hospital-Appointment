package com.cts.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.cts.project.controller.PatientController;
import com.cts.project.dto.PatientDTO;
import com.cts.project.service.PatientService;

class PatientControllerTest {

	@InjectMocks
	private PatientController patientController;

	@Mock
	private PatientService patientService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // ✅ Initializes Mockito annotations
		mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
	}

	@AfterEach
	void tearDown() {
		// ✅ Cleanup logic if needed
	}

	@Test
	void testSavePatient() throws Exception {
		PatientDTO patientDTO = new PatientDTO(1L, "John Doe", LocalDate.of(1990, 5, 20), 32, "Male", "O+", "Jane Doe",
				"9876543210", "john@example.com", "123 Street, City", "No medical history");

		when(patientService.savePatient(any(PatientDTO.class))).thenReturn("Patient details saved successfully.");

		mockMvc.perform(MockMvcRequestBuilders.post("/patients/save").contentType(MediaType.APPLICATION_JSON)
				.content("{\"patientId\":1, \"patientName\":\"John Doe\", \"dateOfBirth\":\"1990-05-20\","
						+ " \"age\":32, \"gender\":\"Male\", \"bloodGroup\":\"O+\", \"guardianName\":\"Jane Doe\","
						+ " \"contactNumber\":\"9876543210\", \"email\":\"john@example.com\","
						+ " \"address\":\"123 Street, City\", \"medicalHistory\":\"No medical history\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Patient details saved successfully."));
	}

	@Test
	void testUpdatePatient() throws Exception {
		PatientDTO patientDTO = new PatientDTO(1L, "Jane Doe", LocalDate.of(1985, 10, 15), 37, "Female", "A-",
				"John Doe", "8765432109", "jane@example.com", "456 Avenue, Town", "Diabetes");

		when(patientService.updatePatient(eq(1L), any(PatientDTO.class)))
				.thenReturn("Patient details updated successfully.");

		mockMvc.perform(MockMvcRequestBuilders.put("/patients/update/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"patientId\":1, \"patientName\":\"Jane Doe\", \"dateOfBirth\":\"1985-10-15\","
						+ " \"age\":37, \"gender\":\"Female\", \"bloodGroup\":\"A-\", \"guardianName\":\"John Doe\","
						+ " \"contactNumber\":\"8765432109\", \"email\":\"jane@example.com\","
						+ " \"address\":\"456 Avenue, Town\", \"medicalHistory\":\"Diabetes\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Patient details updated successfully."));
	}

	@Test
	void testGetAllPatients() throws Exception {
		List<PatientDTO> patients = Arrays.asList(
				new PatientDTO(1L, "John Doe", LocalDate.of(1990, 5, 20), 32, "Male", "O+", "Jane Doe", "9876543210",
						"john@example.com", "123 Street, City", "No medical history"),
				new PatientDTO(2L, "Jane Doe", LocalDate.of(1985, 10, 15), 37, "Female", "A-", "John Doe", "8765432109",
						"jane@example.com", "456 Avenue, Town", "Diabetes"));

		when(patientService.getAllPatients()).thenReturn(patients);

		mockMvc.perform(MockMvcRequestBuilders.get("/patients/getallpatients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	void testGetPatientById() throws Exception {
		PatientDTO patientDTO = new PatientDTO(1L, "John Doe", LocalDate.of(1990, 5, 20), 32, "Male", "O+", "Jane Doe",
				"9876543210", "john@example.com", "123 Street, City", "No medical history");

		when(patientService.getPatientById(1L)).thenReturn(patientDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/patients/getpatientbyid/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.patientName").value("John Doe"));
	}

	@Test
	void testDeletePatient() throws Exception {
		when(patientService.deletePatient(1L, null)).thenReturn("Patient with ID 1 deleted successfully.");

		mockMvc.perform(MockMvcRequestBuilders.delete("/patients/deletepatient/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Patient with ID 1 deleted successfully."));
	}
}
