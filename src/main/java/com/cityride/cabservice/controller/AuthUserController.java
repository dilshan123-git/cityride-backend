
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.AuthUserDTO;
import com.cityride.cabservice.service.AuthUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class AuthUserController {

    private final AuthUserService authUserService;

    // CREATE USER: Admin only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthUserDTO> createUser(
            @RequestBody AuthUserDTO dto) {

        log.info("User creation request received");

        AuthUserDTO createdUser = authUserService.createUser(dto);

        log.info("User created successfully. User ID: {}",
                createdUser.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser);
    }

    // GET ALL USERS: Admin only
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuthUserDTO>> getAllUsers() {

        log.info("Request received to retrieve all users");

        List<AuthUserDTO> users = authUserService.getAllUsers();

        log.info("Retrieved {} users successfully", users.size());

        return ResponseEntity.ok(users);
    }

    // GET USER BY ID: Admin or account owner
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.token.claims['uid']")
    public ResponseEntity<AuthUserDTO> getUserById(
            @PathVariable Integer id) {

        log.info("Request received to retrieve user ID: {}", id);

        AuthUserDTO user = authUserService.getUserById(id);

        log.info("User retrieved successfully. User ID: {}", id);

        return ResponseEntity.ok(user);
    }

    // UPDATE USER: Admin or account owner
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.token.claims['uid']")
    public ResponseEntity<AuthUserDTO> updateUser(
            @PathVariable Integer id,
            @RequestBody AuthUserDTO dto) {

        log.info("User update request received. User ID: {}", id);

        AuthUserDTO updatedUser = authUserService.updateUser(id, dto);

        log.info("User updated successfully. User ID: {}", id);

        return ResponseEntity.ok(updatedUser);
    }

    // DELETE USER: Admin only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(
            @PathVariable Integer id) {

        log.info("User deletion request received. User ID: {}", id);

        authUserService.deleteUser(id);

        log.info("User deleted successfully. User ID: {}", id);

        return ResponseEntity.ok("User deleted successfully");
    }
}
