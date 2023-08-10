package com.telerikacademy.carpooling.models;

import javax.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengers_id")
    private int passenger;
    private int userId;

}
