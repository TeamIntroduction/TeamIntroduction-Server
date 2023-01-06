package com.example.project.dto;

import com.example.project.constant.SuccessResponse;
import com.example.project.utils.response.ResponseStatus;
import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private final ResponseStatus status;
    private final String code;
    private final String message;
    private final T data;

    public ResponseDto(SuccessResponse successResponse, T data) {
        this.status = ResponseStatus.SUCCESS;
        this.code = successResponse.getCode();
        this.message = successResponse.getMessage();
        this.data = data;
    }
}
