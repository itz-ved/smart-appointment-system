package com.ved.appointment.controller;

import com.ved.appointment.entity.Provider;
import com.ved.appointment.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    // ✅ ADD PROVIDER
    @PostMapping("/add")
    public ResponseEntity<String> addProvider(@RequestBody Provider provider) {
        try {
            providerService.addProvider(provider);
            return ResponseEntity.ok("Provider Registered Successfully ✅");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginProvider(@RequestBody Map<String, String> data) {
        return ResponseEntity.ok(
                providerService.loginProvider(
                        data.get("email"),
                        data.get("password")
                )
        );
    }

    // ✅ GET ALL PROVIDERS
    @GetMapping("/all")
    public ResponseEntity<List<Provider>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    // ✅ GET BY SERVICE TYPE
    @GetMapping("/type/{serviceType}")
    public ResponseEntity<List<Provider>> getProviderByType(@PathVariable String serviceType) {
        return ResponseEntity.ok(providerService.getProviderByType(serviceType));
    }
}