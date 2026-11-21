package com.cityride.cabservice.mapper;


import com.cityride.cabservice.dto.VehicleDTO;
import com.cityride.cabservice.entity.Vehicle;

public class VehicleMapper {

    public static VehicleDTO convertToDTO(Vehicle vehicle) {

        VehicleDTO dto = new VehicleDTO();

        dto.setVehicleId(vehicle.getVehicleId());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());

        dto.setManufactureYear(
                vehicle.getManufactureYear() != null
                        ? vehicle.getManufactureYear().getValue()
                        : null
        );

        dto.setSeatingCapacity(vehicle.getSeatingCapacity());
        dto.setStatus(vehicle.getStatus());

        return dto;
    }
}
