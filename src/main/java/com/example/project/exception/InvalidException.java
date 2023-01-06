package com.example.project.exception;

import com.example.project.constant.ErrorResponse;
import lombok.Getter;

@Getter
public class InvalidException extends CustomException {

    public InvalidException(ErrorResponse errorResponse) {
        super(errorResponse.getCode(), errorResponse.getMessage());
    }
}
