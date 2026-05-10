package com.ved.appointment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SlotAlreadyBookedException.class)
    public ResponseEntity<?> handleSlotBooked(SlotAlreadyBookedException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", 400);

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(AppointmentAlreadyCancelledException.class)
    public ResponseEntity<?> handleAlreadyCancelled(AppointmentAlreadyCancelledException ex)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", 400);

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(RescheduleNotAllowedException.class)
    public ResponseEntity<?> handleReschedule(RescheduleNotAllowedException ex)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", 400);

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", 400);

        return ResponseEntity.badRequest().body(response);
    }
}