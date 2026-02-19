package com.ved.appointment.repository;

import com.ved.appointment.entity.Provider;
import com.ved.appointment.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public  interface SlotRepository extends JpaRepository<Slot,Long> {
    //Provider ke saare slots
    List<Slot> findByProvider(Provider provider);
    //provider +date ke slots
    List<Slot> findByProviderAndDate(Provider provider, LocalDate date);


    List<Slot> findByProviderAndDateAndStatus(Provider provider, LocalDate date, String status);

}
