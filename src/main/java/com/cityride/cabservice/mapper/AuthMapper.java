package com.cityride.cabservice.mapper;

import com.cityride.cabservice.dto.AuthUserDTO;
import com.cityride.cabservice.entity.AuthUser;

public class AuthMapper {

    public static AuthUserDTO convertToDTO(AuthUser user) {

        AuthUserDTO dto = new AuthUserDTO();

        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setUserStatus(user.getUserStatus());

        return dto;
    }

}
