package com.cts.project.controller;


import com.cts.project.dto.NotificationDTO;
import com.cts.project.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

   private NotificationService service;

   @PostMapping("/send")
   public NotificationDTO send(@Valid @RequestBody NotificationDTO dto) {
       return service.sendNotification(dto);
   }

   @GetMapping("/getnotificationbypatientid/{patientId}")
   public List<NotificationDTO> getByPatient(@PathVariable Long patientId) {
       return service.getNotificationsByPatientId(patientId);
   }
}
