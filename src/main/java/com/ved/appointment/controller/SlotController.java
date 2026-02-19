package com.ved.appointment.controller;

import com.ved.appointment.entity.Slot;
import com.ved.appointment.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PostMapping("/add/{providerId}")
    public Slot createSlot(@PathVariable Long providerId, @RequestBody Slot slot){
        return slotService.createSlot(providerId, slot);
    }

    @GetMapping("/provider/{providerId}")
    public List<Slot> getSlotsByProvider(@PathVariable Long providerId){
        return slotService.getSlotByProvider(providerId);
    }

    @GetMapping("/provider/{providerId}/{date}")
    public List<Slot> getSlotsByProviderAndDate(@PathVariable Long providerId,
                                                @PathVariable LocalDate date){
        return slotService.getSlotByProviderAndDate(providerId, date);
    }

    // show only free slots
    @GetMapping("/available/{providerId}/{date}")
    public List<Slot> getAvailableSlots(@PathVariable Long providerId,
                                        @PathVariable LocalDate date){
        return slotService.getAvailableSlots(providerId, date);
    }
}
