package com.ved.appointment.service;

import com.ved.appointment.entity.Provider;
import com.ved.appointment.entity.Slot;
import com.ved.appointment.entity.Status;
import com.ved.appointment.repository.ProviderRepository;
import com.ved.appointment.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime; // ✅ already added
import java.util.List;

@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private ProviderRepository providerRepository;

    public Slot createSlot(Long providerId, Slot slot)
    {
        // ✅ Validation
        if(providerId == null) {
            throw new RuntimeException("Provider ID is required");
        }

        if(slot.getDate() == null) {
            throw new RuntimeException("Date is required");
        }

        if(slot.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot create slot for past date");
        }

        // 🔥🔥 ADD THIS (IMPORTANT FIX)
        if (slot.getDate().equals(LocalDate.now())) {

            LocalTime now = LocalTime.now();

            if (slot.getStartTime().isBefore(now)) {
                throw new RuntimeException("Slot time already passed ❌");
            }
        }

        if(slot.getStartTime() == null || slot.getEndTime() == null) {
            throw new RuntimeException("Start time and end time are required");
        }

        if (slot.getStartTime().isAfter(slot.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        // ✅ Business logic
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        // 🔥 DUPLICATE CHECK
        boolean exists = slotRepository.existsByProviderAndDateAndStartTimeAndEndTime(
                provider,
                slot.getDate(),
                slot.getStartTime(),
                slot.getEndTime()
        );

        if (exists) {
            throw new RuntimeException("Slot already exists");
        }

        // 🔥 OVERLAP CHECK
        List<Slot> existingSlots = slotRepository.findByProviderAndDate(provider, slot.getDate());

        for (Slot s : existingSlots) {
            if (slot.getStartTime().isBefore(s.getEndTime()) &&
                    slot.getEndTime().isAfter(s.getStartTime())) {

                throw new RuntimeException("Slot overlaps with existing slot");
            }
        }

        slot.setProvider(provider);
        slot.setStatus(Status.AVAILABLE);

        return slotRepository.save(slot);
    }

    public List<Slot> getSlotByProvider(Long providerId)
    {
        Provider provider=providerRepository.findById(providerId)
                .orElseThrow(()->new RuntimeException("Provider not found"));
        return slotRepository.findByProvider(provider);
    }

    public List<Slot> getSlotByProviderAndDate(Long providerId, LocalDate date)
    {
        Provider provider=providerRepository.findById(providerId)
                .orElseThrow(()->new RuntimeException("Provider not found"));

        return slotRepository.findByProviderAndDate(provider,date);
    }

    // 🔥 FIXED METHOD (IMPORTANT)
    public List<Slot> getAvailableSlots(Long providerId, LocalDate date){

        if (date.isBefore(LocalDate.now())) {
            return List.of();
        }

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        List<Slot> slots = slotRepository.findByProviderAndDateAndStatus(provider, date, Status.AVAILABLE);

        if (date.equals(LocalDate.now())) {

            LocalTime now = LocalTime.now();

            return slots.stream()
                    .filter(s -> s.getStartTime().isAfter(now))
                    .toList();
        }

        return slots;
    }
}