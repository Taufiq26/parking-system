package com.example.parkingsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer base_price;

    public VehicleType () {
        
    }

    public VehicleType (String name, Integer base_price) {
        this.id = null;
        this.name = name;
        this.base_price = base_price;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getBasePrice() {
        return base_price;
    }
    public void setBasePrice(Integer base_price) {
        this.base_price = base_price;
    }
}
