package com.ved.appointment.controller;

import com.ved.appointment.entity.Provider;
import com.ved.appointment.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {
    @Autowired
    private ProviderService providerService;
    @PostMapping("/add")
    public Provider addProvider(@RequestBody Provider provider)
    {
        return providerService.addProvider(provider);
    }
    @GetMapping("/all")
    public List<Provider> getAllProviders()
    {
        return providerService.getAllProviders();
    }
    @GetMapping("/type/{serviceType}")
    public List<Provider>getProviderByType(@PathVariable String serviceType){
        return providerService.getProviderByType(serviceType);
    }
}
