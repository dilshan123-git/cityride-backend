
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.LoginRequestDto;
import com.cityride.cabservice.dto.LoginResponseDto;
import com.cityride.cabservice.service.impl.LoginAuthServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final LoginAuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        logger.info("Login request received");
        logger.debug("Validating login request");
        logger.info("Authenticating user");
        LoginResponseDto response = authService.login(request);
        logger.info("Login completed successfully");

        return ResponseEntity.ok(response);
    }
}
