package com.example.project.dto;

import com.example.project.controller.annotation.Dec;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReqDto {

    private String username;
    @Dec
    private String password;
}
