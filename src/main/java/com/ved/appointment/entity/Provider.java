package com.ved.appointment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String serviceType;
    private String experience;
    private String location;
    private double fees;
    @Column(length = 10,nullable = false)
    private String phone;


}
