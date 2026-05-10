package com.ved.appointment.service;

import com.ved.appointment.entity.*;
import com.ved.appointment.exception.AppointmentAlreadyCancelledException;
import com.ved.appointment.exception.RescheduleNotAllowedException;
import com.ved.appointment.exception.SlotAlreadyBookedException;
import com.ved.appointment.repository.AppointmentRepository;
import com.ved.appointment.repository.ProviderRepository;
import com.ved.appointment.repository.SlotRepository;
import com.ved.appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private SlotService slotService;

    public Appointment bookAppointment(Long userId, Long providerId, Long slotId)
    {
        if(userId == null || providerId == null || slotId == null) {
            throw new RuntimeException("Invalid booking request");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (!Status.AVAILABLE.equals(slot.getStatus()))
        {
            throw new SlotAlreadyBookedException("Slot already booked");
        }

        slot.setStatus(Status.BOOKED);
        slotRepository.save(slot);

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setProvider(provider);
        appointment.setSlot(slot);
        appointment.setStatus(Status.BOOKED);

        return appointmentRepository.save(appointment);
    }
    public void cancelAppointment(Long appointmentId)
    {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (Status.CANCELLED.equals(appointment.getStatus()))
        {
            throw new AppointmentAlreadyCancelledException("Already cancelled");
        }

        Slot slot = appointment.getSlot();

        // ✅ slot free
        slot.setStatus(Status.AVAILABLE);
        slotRepository.save(slot);

        // ✅ keep history
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);
    }
    public Appointment rescheduleAppointment(Long appointmentId, Long newSlotId)
    {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (Status.CANCELLED.equals(appointment.getStatus()))
        {
            throw new RescheduleNotAllowedException("Cannot reschedule cancelled appointment");
        }

        Slot oldSlot = appointment.getSlot();

        Slot newSlot = slotRepository.findById(newSlotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (oldSlot.getId().equals(newSlotId))
        {
            throw new RescheduleNotAllowedException("Already booked on this slot");
        }

        if (!Status.AVAILABLE.equals(newSlot.getStatus()))
        {
            throw new RescheduleNotAllowedException("New slot not available");
        }

        oldSlot.setStatus(Status.AVAILABLE);
        slotRepository.save(oldSlot);

        newSlot.setStatus(Status.BOOKED);
        slotRepository.save(newSlot);

        appointment.setSlot(newSlot);

        // 🔥 ADD THIS
        appointment.setStatus(Status.BOOKED);

        return appointmentRepository.save(appointment);
    }
    public List<Appointment> getByUser(Long userId){
        return appointmentRepository.findByUserId(userId);
    }
    public List<Appointment> getByProvider(Long providerId){
        return appointmentRepository.findByProviderId(providerId);
    }
}
