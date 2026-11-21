package com.cityride.cabservice.dto;

import com.cityride.cabservice.enums.Availability;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDTO {

    private Integer driverId;
    private String fullName;
    private String phone;
    private String email;
    private String licenseNumber;
    private String address;
    private Availability availability;
}