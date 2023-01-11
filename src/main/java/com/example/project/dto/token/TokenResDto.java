package com.example.project.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResDto {

    private String accessToken;
    private String refreshToken;
}
