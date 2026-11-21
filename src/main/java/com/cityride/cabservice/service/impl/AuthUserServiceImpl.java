package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.AuthUserDTO;
import com.cityride.cabservice.entity.AuthUser;
import com.cityride.cabservice.enums.Roles;
import com.cityride.cabservice.enums.UserStatus;
import com.cityride.cabservice.mapper.AuthMapper;
import com.cityride.cabservice.repository.AuthUserRepository;
import com.cityride.cabservice.service.AuthUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE USER
    @Override
    public AuthUserDTO createUser(AuthUserDTO dto) {

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password is required"
            );
        }

        if (authUserRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        AuthUser user = new AuthUser();

        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : Roles.CUSTOMER);
        user.setUserStatus(dto.getUserStatus() != null
                ? dto.getUserStatus()
                : UserStatus.ACTIVE);

        return AuthMapper.convertToDTO(authUserRepository.save(user));
    }

    // GET ALL USERS
    @Override
    @Transactional(readOnly = true)
    public List<AuthUserDTO> getAllUsers() {

        return authUserRepository.findAll()
                .stream()
                .map(AuthMapper::convertToDTO)
                .toList();
    }

    // GET USER BY ID
    @Override
    @Transactional(readOnly = true)
    public AuthUserDTO getUserById(Integer id) {

        AuthUser user = authUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        return AuthMapper.convertToDTO(user);
    }

    // UPDATE USER
    @Override
    public AuthUserDTO updateUser(Integer id, AuthUserDTO dto) {

        AuthUser user = authUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        if (authUserRepository.existsByEmailAndUserIdNot(
                dto.getEmail(), id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        if (dto.getUserStatus() != null) {
            user.setUserStatus(dto.getUserStatus());
        }

        return AuthMapper.convertToDTO(authUserRepository.save(user));
    }

    // DELETE USER
    @Override
    public void deleteUser(Integer id) {

        AuthUser user = authUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        authUserRepository.delete(user);
    }
}