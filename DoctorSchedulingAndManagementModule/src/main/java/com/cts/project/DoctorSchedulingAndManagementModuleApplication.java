package com.cts.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Doctor Scheduling and Management Module.
 * This application enables doctor scheduling features and integrates with external services via Feign Clients.
 */
@SpringBootApplication // Marks this class as a Spring Boot application.
@EnableFeignClients(basePackages = "com.cts.project.feignclient") // Enables Feign Clients for remote service calls.
public class DoctorSchedulingAndManagementModuleApplication {

    /**
     * The entry point of the Spring Boot application.
     * @param args Command-line arguments (if any).
     */
    public static void main(String[] args) {
        SpringApplication.run(DoctorSchedulingAndManagementModuleApplication.class, args);
    }
}
