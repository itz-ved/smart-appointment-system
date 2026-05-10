package com.ved.appointment.service;

import com.ved.appointment.entity.Provider;
import com.ved.appointment.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;
    public Provider addProvider(Provider provider) {

        // ✅ STEP 1: Validation (TOP pe add karo)
        if(provider.getName() == null || provider.getName().isEmpty()) {
            throw new RuntimeException("Provider name is required");
        }

        if(provider.getServiceType() == null || provider.getServiceType().isEmpty()) {
            throw new RuntimeException("Service type is required");
        }

        if(provider.getEmail() == null || provider.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if(provider.getPhone() == null || provider.getPhone().isEmpty()) {
            throw new RuntimeException("Phone is required");
        }

        // ✅ STEP 2: Existing duplicate checks (unchanged)
        if (providerRepository.findByPhone(provider.getPhone()) != null) {
            throw new RuntimeException("Phone already registered ❌");
        }

        if (providerRepository.findByEmail(provider.getEmail()) != null) {
            throw new RuntimeException("Email already registered ❌");
        }

        // ✅ STEP 3: Save
        return providerRepository.save(provider);
    }
    public Map<String, Object> loginProvider(String email, String password){

        Provider provider = providerRepository.findByEmail(email);

        if(provider == null){
            return Map.of("message", "Provider not found");
        }

        if(provider.getPassword() == null || !provider.getPassword().equals(password)){
            return Map.of("message", "Invalid password");
        }

        return Map.of(
                "message", "Login successful",
                "providerId", provider.getId(),
                "name", provider.getName()
        );
    }
    public List<Provider> getAllProviders()
    {
        return providerRepository.findAll();
    }
    public List<Provider>getProviderByType(String type)
    {
        return providerRepository.findByServiceType(type);
    }
}
