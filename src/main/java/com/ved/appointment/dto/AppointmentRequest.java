package com.ved.appointment.dto;

import lombok.Data;

@Data
public class AppointmentRequest {

    private Long userId;
    private Long providerId;
    private Long slotId;

    // getters setters
}