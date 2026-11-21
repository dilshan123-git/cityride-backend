package com.cityride.cabservice.repository;

import com.cityride.cabservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    boolean existsByVehicleNumber(String vehicleNumber);

    boolean existsByVehicleNumberAndVehicleIdNot(
            String vehicleNumber, Integer vehicleId
    );
}
