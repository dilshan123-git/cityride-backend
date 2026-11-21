package com.cityride.cabservice.dto;

import com.cityride.cabservice.enums.Availability;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}