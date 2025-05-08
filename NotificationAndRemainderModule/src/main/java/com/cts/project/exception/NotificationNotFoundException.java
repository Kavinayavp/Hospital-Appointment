package com.cts.project.exception;


public class NotificationNotFoundException extends RuntimeException {
   public NotificationNotFoundException(String message) {
       super(message);
   }
}
