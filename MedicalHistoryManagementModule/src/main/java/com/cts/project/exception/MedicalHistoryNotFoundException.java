package com.cts.project.exception;

public class MedicalHistoryNotFoundException extends RuntimeException {
	public MedicalHistoryNotFoundException(String message) {
		super(message);
	}
}