package com.example.project.dto;

import com.example.project.constant.ErrorResponse;
import com.example.project.constant.SuccessResponse;
import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private final ResponseStatus status;
    private final String code;
    private final String message;
    private T data;

    private ResponseDto(ResponseStatus status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ResponseDto(ResponseStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseDto<T> success(SuccessResponse successResponse, T data) {
        return new ResponseDto(successResponse.getStatus(), successResponse.getCode(), successResponse.getMessage(), data);
    }

    public static ResponseDto error(ErrorResponse errorResponse) {
        return new ResponseDto(errorResponse.getStatus(), errorResponse.getCode(), errorResponse.getMessage());
    }

    public static ResponseDto error(String code, String message) {
        return new ResponseDto(ResponseStatus.ERROR, code, message);
    }
}
