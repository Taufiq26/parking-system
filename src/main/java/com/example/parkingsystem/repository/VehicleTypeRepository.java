package com.example.parkingsystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.parkingsystem.model.VehicleType;

public interface VehicleTypeRepository extends CrudRepository<VehicleType, Integer> {
    
}
