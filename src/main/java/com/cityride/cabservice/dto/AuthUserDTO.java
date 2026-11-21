package com.cityride.cabservice.dto;

import com.cityride.cabservice.enums.Roles;
import com.cityride.cabservice.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDTO {

    private Integer userId;
    private String email;
    private String password;
    private Roles role;
    private UserStatus userStatus;
}