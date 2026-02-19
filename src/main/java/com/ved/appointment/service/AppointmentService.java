package com.ved.appointment.service;

import com.ved.appointment.entity.Appointment;
import com.ved.appointment.entity.Provider;
import com.ved.appointment.entity.Slot;
import com.ved.appointment.entity.User;
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

    public Appointment bookAppointment(Long userId,Long providerId,Long slotId)
    {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not Found"));
        Provider provider=providerRepository.findById(providerId)
                .orElseThrow(()->new RuntimeException("Provider not found"));
        Slot slot=slotRepository.findById(slotId)
                .orElseThrow(()->new RuntimeException("Slot not found"));
        if(!slot.getStatus().equals("AVAILABLE"))
        {
            throw new RuntimeException("Slot already booked");
        }
        slot.setStatus("BOOKED");;
        slotRepository.save(slot);
        Appointment appointment=new Appointment();
        appointment.setUser(user);
        appointment.setProvider(provider);
        appointment.setSlot(slot);
        appointment.setStatus("BOOKED");
        return appointmentRepository.save(appointment);
    }
    public List<Appointment> getUserAppointments(Long userId)
    {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        return appointmentRepository.findByUser(user);
    }
}
