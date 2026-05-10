package com.ved.appointment.exception;

public class RescheduleNotAllowedException extends RuntimeException {

    public RescheduleNotAllowedException(String message) {
        super(message);
    }
}