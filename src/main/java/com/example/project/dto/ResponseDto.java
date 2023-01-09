package com.example.project.dto;

import com.example.project.constant.ErrorResponse;
import com.example.project.constant.SuccessResponse;
import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private final ResponseStatus status;
    private final String code;
    private final String message;
    private final T data;

    private ResponseDto(ResponseStatus status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseDto<T> success(SuccessResponse successResponse, T data) {
        return new ResponseDto(successResponse.getStatus(), successResponse.getCode(), successResponse.getMessage(), data);
    }

    public static <T> ResponseDto<T> error(ErrorResponse errorResponse, T data) {
        return new ResponseDto(errorResponse.getStatus(), errorResponse.getCode(), errorResponse.getMessage(), data);
    }

    public static <T> ResponseDto<T> error(String message, String code, T data) {
        return new ResponseDto(ResponseStatus.ERROR, message, code, data);
    }
}
