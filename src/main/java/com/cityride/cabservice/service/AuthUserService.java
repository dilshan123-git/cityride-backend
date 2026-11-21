package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.AuthUserDTO;

import java.util.List;

public interface AuthUserService {

    AuthUserDTO createUser(AuthUserDTO authUserDTO);

    List<AuthUserDTO> getAllUsers();

    AuthUserDTO getUserById(Integer id);

    AuthUserDTO updateUser(Integer id, AuthUserDTO authUserDTO);

    void deleteUser(Integer id);
}