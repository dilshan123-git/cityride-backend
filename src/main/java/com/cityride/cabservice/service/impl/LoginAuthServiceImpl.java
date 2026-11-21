
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.AccessTokenDto;
import com.cityride.cabservice.dto.LoginRequestDto;
import com.cityride.cabservice.dto.LoginResponseDto;
import com.cityride.cabservice.entity.AuthUser;
import com.cityride.cabservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginAuthServiceImpl {

    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponseDto login(LoginRequestDto request) {

        log.info("Login request received");

        try {
            log.debug("Searching user by email");

            AuthUser user = userRepository.findByEmail(
                            request.getEmail().trim().toLowerCase()
                    )
                    .filter(u -> passwordEncoder.matches(
                            request.getPassword(),
                            u.getPassword()
                    ))
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Invalid email or password"
                    ));

            log.info("User found successfully");

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                log.warn("Login failed: Invalid password");
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                );
            }

            log.info("User authentication successful");

            AccessTokenDto accessTokenDto = new AccessTokenDto();
            accessTokenDto.setRole(user.getRole().toString());
            accessTokenDto.setExpiresInSeconds(tokenService.getExpiresInSeconds());
            accessTokenDto.setTokenType("Login Token");

            log.debug("Generating access token");

            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setAccessToken(tokenService.generateToken(user));
            loginResponseDto.setMessage("Login successful");

            log.info("Access token generated successfully");
            log.info("Login completed successfully");

            return loginResponseDto;

        } catch (ResponseStatusException ex) {
            log.warn("Login failed: {}", ex.getReason());
            throw ex;

        } catch (Exception ex) {
            log.error("Unexpected error occurred during login", ex);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred during login"
            );
        }
    }
}
