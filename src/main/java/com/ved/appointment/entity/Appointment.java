package com.ved.appointment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Kis user ne book kiya
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //kis provider ke sath
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    //Kaun sa slot book hua
    @OneToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;
    //Booker/Cancelled/Completed
    private String Status;


}
