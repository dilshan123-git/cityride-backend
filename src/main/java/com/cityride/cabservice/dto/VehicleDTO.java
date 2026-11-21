package com.cityride.cabservice.dto;

import com.cityride.cabservice.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private Integer vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String brand;
    private String model;
    private Integer manufactureYear;
    private Integer seatingCapacity;
    private VehicleStatus status;
}