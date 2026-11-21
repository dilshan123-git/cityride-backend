package com.cityride.cabservice.config;

import com.cityride.cabservice.entity.AuthUser;
import com.cityride.cabservice.enums.Roles;
import com.cityride.cabservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Bean
    CommandLineRunner seedAdmin(AuthUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (!repo.existsByEmail("admin@cityride.com")) {
                AuthUser admin = new AuthUser();
                admin.setEmail("admin@cityride.com");
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setRole(Roles.ADMIN);
                repo.save(admin);
            }
        };
    }
}