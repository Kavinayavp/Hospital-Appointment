package com.cts.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Appointment Scheduling Module. This application handles
 * appointment management and integrates with external services via Feign
 * Clients.
 */
@SpringBootApplication // Marks this class as a Spring Boot application.
@EnableFeignClients(basePackages = "com.cts.project.feignclient") // Enables Feign Clients for remote service calls.
public class AppointmentSchedulingModuleApplication {

	/**
	 * The entry point of the Spring Boot application.
	 * 
	 * @param args Command-line arguments (if any).
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppointmentSchedulingModuleApplication.class, args);
	}
}
