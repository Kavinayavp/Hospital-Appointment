package com.cts.project;

import com.cts.project.dto.NotificationDTO;
import com.cts.project.model.Notification;
import com.cts.project.repository.NotificationRepository;
import com.cts.project.service.NotificationServiceImpl;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationAndRemainderModuleApplicationTests {

	private final NotificationRepository repo = mock(NotificationRepository.class);
	private final NotificationServiceImpl service = new NotificationServiceImpl(repo, null, null, null);

	@Test
	void testSendNotification() {
		NotificationDTO dto = NotificationDTO.builder().patientId(1L).message("Your appointment is tomorrow.")
				.timestamp(LocalDateTime.now()).build();

		Notification entity = Notification.builder().notificationId(1L).patientId(1L)
				.message("Your appointment is tomorrow.").timestamp(LocalDateTime.now()).build();

		when(repo.save(any())).thenReturn(entity);

		NotificationDTO result = service.sendNotification(dto);
		assertEquals("Your appointment is tomorrow.", result.getMessage());
	}
}