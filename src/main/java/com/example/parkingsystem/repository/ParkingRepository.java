package com.example.parkingsystem.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import com.example.parkingsystem.model.Parking;

@Repository
public interface ParkingRepository extends CrudRepository<Parking, Integer> {
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = 
        "UPDATE parking AS p SET p.parking_out=:date_out WHERE p.police_number=:police_number AND p.parking_out IS NULL"
    )
    void doParkingOut(@Param("police_number") String police_number, @Param("date_out") Timestamp date_out);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = 
        "UPDATE parking AS p SET p.parking_out=:date_out WHERE p.id=:id"
    )
    void doParkingOutById(@Param("id") Integer id, @Param("date_out") Timestamp date_out);

    @Query(nativeQuery = true, value = 
        "SELECT p.* "+
        "FROM parking AS p " +
        "WHERE p.police_number=:police_number  " +
        "AND p.parking_out IS NULL"
    )
    Parking findOneWhoStillParking(@Param("police_number") String police_number);

    @Query(nativeQuery = true, value = 
        "SELECT p.*, vt.name as vehicle_name "+
        "FROM parking AS p " +
        "INNER JOIN vehicle_type as vt ON vt.id = p.vehicle_type " +
        "WHERE p.parking_out IS NULL"
    )
    List<Object[]> findWhoStillParking();
}
