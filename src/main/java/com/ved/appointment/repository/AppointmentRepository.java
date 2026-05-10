package com.ved.appointment.repository;

import com.ved.appointment.entity.Appointment;
import com.ved.appointment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByUser(User user);
    List<Appointment> findByUserId(Long userId);
    List<Appointment> findByProviderId(Long providerId);
}
