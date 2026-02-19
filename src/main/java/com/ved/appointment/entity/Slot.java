package com.ved.appointment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name ="slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //One provider ->many slots
    @ManyToOne
    @JoinColumn(name="provider_id")
    private Provider provider;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    //Available or Booked
    private String status;
}
