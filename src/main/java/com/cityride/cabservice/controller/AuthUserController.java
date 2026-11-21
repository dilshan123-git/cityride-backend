package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.AuthUserDTO;
import com.cityride.cabservice.service.AuthUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthUserDTO> createUser(@RequestBody AuthUserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authUserService.createUser(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuthUserDTO>> getAllUsers() {
        return ResponseEntity.ok(authUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.token.claims['uid']")
    public ResponseEntity<AuthUserDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(authUserService.getUserById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.token.claims['uid']")
    public ResponseEntity<AuthUserDTO> updateUser(@PathVariable Integer id,
                                                  @RequestBody AuthUserDTO dto) {
        return ResponseEntity.ok(authUserService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        authUserService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}