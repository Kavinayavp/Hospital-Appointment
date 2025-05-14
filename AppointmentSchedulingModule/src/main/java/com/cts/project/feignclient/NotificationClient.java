package com.cts.project.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "NOTIFICATIONANDREMAINDERMODULE", url = "http://localhost:8082/notifications")
public interface NotificationClient {

    @GetMapping("/appointments/{appointmentId}/notifyDoctor")
    String notifyDoctor(@PathVariable Long appointmentId);
}
