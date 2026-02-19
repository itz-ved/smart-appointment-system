package com.ved.appointment.service;

import com.ved.appointment.entity.Provider;
import com.ved.appointment.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;
    public Provider addProvider(Provider provider)
    {
        return providerRepository.save(provider);
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
