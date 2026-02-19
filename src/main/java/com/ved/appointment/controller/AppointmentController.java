package com.ved.appointment.controller;

import com.ved.appointment.entity.Appointment;
import com.ved.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // book appointment
    @PostMapping("/book")
    public Appointment bookAppointment(@RequestParam Long userId,
                                       @RequestParam Long providerId,
                                       @RequestParam Long slotId){
        return appointmentService.bookAppointment(userId, providerId, slotId);
    }

    // get user appointments
    @GetMapping("/user/{userId}")
    public List<Appointment> getUserAppointments(@PathVariable Long userId){
        return appointmentService.getUserAppointments(userId);
    }
}
