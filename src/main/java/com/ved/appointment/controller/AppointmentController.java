package com.ved.appointment.controller;

import com.ved.appointment.dto.AppointmentRequest;
import com.ved.appointment.entity.Appointment;
import com.ved.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // ✅ BOOK APPOINTMENT
    @PostMapping("/book")
    public Map<String, Object> bookAppointment(@RequestBody AppointmentRequest request)
    {
        Appointment appointment = appointmentService.bookAppointment(
                request.getUserId(),
                request.getProviderId(),
                request.getSlotId()
        );

        return Map.of(
                "message", "Appointment booked successfully",
                "appointmentId", appointment.getId(),
                "status", appointment.getStatus(),
                "providerName", appointment.getProvider().getName(),
                "slotTime", appointment.getSlot().getStartTime()
        );
    }

    // ✅ CANCEL
    @PutMapping("/cancel/{id}")
    public Map<String, String> cancelAppointment(@PathVariable Long id)
    {
        appointmentService.cancelAppointment(id);
        return Map.of("message", "Appointment cancelled successfully");
    }

    // ✅ GET USER APPOINTMENTS (ONLY ONE METHOD)
    @GetMapping("/user/{userId}")
    public List<Appointment> getByUser(@PathVariable Long userId){
        return appointmentService.getByUser(userId);
    }

    // ✅ RESCHEDULE (FIXED PATH BASED)
    @PutMapping("/reschedule/{appointmentId}/{slotId}")
    public Appointment reschedule(
            @PathVariable Long appointmentId,
            @PathVariable Long slotId){

        return appointmentService.rescheduleAppointment(appointmentId, slotId);
    }
    @GetMapping("/provider/{providerId}")
    public List<Appointment> getByProvider(@PathVariable Long providerId){
        return appointmentService.getByProvider(providerId);
    }
}
