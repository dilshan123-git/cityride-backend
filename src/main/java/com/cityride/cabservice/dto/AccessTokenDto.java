package com.cityride.cabservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessTokenDto {
    Integer userId;
    String email;
    String tokenType;
    long expiresInSeconds;
    String role;
}
