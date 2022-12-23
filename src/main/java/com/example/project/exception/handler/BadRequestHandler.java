package com.example.project.exception.handler;

import com.example.project.dto.ResponseDto;
import com.example.project.exception.InvalidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.project.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            InvalidException.class
    })
    public ResponseDto handle400(Exception exception) {
        System.out.println("❗❗️ exception = " + exception);

        return ResponseUtil.ERROR(exception.getMessage(), null);
    }
}
