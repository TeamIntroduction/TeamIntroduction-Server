package com.example.project.utils.response;

import com.example.project.dto.ResponseDto;
import org.springframework.http.HttpStatus;

public class ResponseUtil {

    public static <T> ResponseDto<T> SUCCESS(HttpStatus status, String message, T data) {
        return new ResponseDto(status.value(), message, data);
    }

    public static <T> ResponseDto<T> ERROR(HttpStatus status, String code, String message, T data) {
        return new ResponseDto(status.value(), code, message, data);
    }
}
