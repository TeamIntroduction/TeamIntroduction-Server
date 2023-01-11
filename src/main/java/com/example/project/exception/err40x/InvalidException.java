package com.example.project.exception.err40x;

import com.example.project.constant.ErrorResponse;
import com.example.project.exception.CustomException;
import lombok.Getter;

@Getter
public class InvalidException extends CustomException {

    public InvalidException(ErrorResponse errorResponse) {
        super(errorResponse.getCode(), errorResponse.getMessage());
    }
}
