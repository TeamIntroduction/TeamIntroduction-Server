package com.example.project.exception.err40x;

import com.example.project.constant.ErrorResponse;
import com.example.project.exception.CustomException;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(ErrorResponse errorResponse){
        super(errorResponse.getCode(), errorResponse.getMessage());
    }
}
