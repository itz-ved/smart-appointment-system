package com.ved.appointment.repository;

import com.ved.appointment.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ProviderRepository extends JpaRepository<Provider,Long> {
    //Multi domain Like Docter,Salon,Tutor
    List<Provider> findByServiceType(String serviceType );
    Provider findByPhone(String phone);
    Provider findByEmail(String email);
}
