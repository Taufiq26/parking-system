package com.example.parkingsystem.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parking {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer vehicle_type;
    private String police_number;
    private Timestamp parking_in;
    private Timestamp parking_out;

    public Integer getId() {
    return id;
    }
    public void setId(Integer id) {
    this.id = id;
    }

    public Integer getVehicleType() {
    return vehicle_type;
    }
    public void setVehicleType(Integer vehicle_type) {
    this.vehicle_type = vehicle_type;
    }

    public String getPoliceNumber() {
    return police_number;
    }
    public void setPoliceNumber(String police_number) {
    this.police_number = police_number;
    }

    public Timestamp getParkingIn() {
    return parking_in;
    }
    public void setParkingIn(Timestamp parking_in) {
    this.parking_in = parking_in;
    }

    public Timestamp getParkingOut() {
    return parking_out;
    }
    public void setParkingOut(Timestamp parking_out) {
    this.parking_out = parking_out;
    }
}
