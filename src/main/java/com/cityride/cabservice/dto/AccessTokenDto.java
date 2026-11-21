package com.cityride.cabservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessTokenDto {
    String tokenType;
    long expiresInSeconds;
    String role;
}
