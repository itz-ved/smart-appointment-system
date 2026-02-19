package com.ved.appointment.service;

import com.ved.appointment.entity.Provider;
import com.ved.appointment.entity.Slot;
import com.ved.appointment.repository.ProviderRepository;
import com.ved.appointment.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private ProviderRepository providerRepository;
    public Slot createSlot(Long providerId,Slot slot)
    {
        Provider provider=providerRepository.findById(providerId)
                .orElseThrow(()-> new RuntimeException("Provider not found"));
        slot.setProvider(provider);
        slot.setStatus("AVAILABLE");
        return slotRepository.save(slot);
    }
    public List<Slot> getSlotByProvider(Long providerId)
    {
        Provider provider=providerRepository.findById(providerId)
                .orElseThrow(()->new RuntimeException("Provider not found"));
        return slotRepository.findByProvider(provider);
    }
    public List<Slot>getSlotByProviderAndDate(Long providerId, LocalDate date)
    {
        Provider provider=providerRepository.findById(providerId)
                .orElseThrow(()->new RuntimeException("Provider not found"));

        return slotRepository.findByProviderAndDate(provider,date);
    }
    // get available slots
    public List<Slot> getAvailableSlots(Long providerId, LocalDate date){

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        return slotRepository.findByProviderAndDateAndStatus(provider, date, "AVAILABLE");
    }

}
