package com.example.project.exception.handler;

import com.example.project.dto.ResponseDto;
import com.example.project.exception.CustomException;
import com.example.project.exception.InvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.project.constant.ErrorResponse.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class BadRequestHandler {

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ResponseDto> handle400Error(Exception exception) {
        log.error("❗❗️ exception = " + exception);
        return new ResponseEntity<>(new ResponseDto(BAD_REQUEST.getCode(), BAD_REQUEST.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            InvalidException.class
    })
    public ResponseEntity<ResponseDto> handleCustom400Error(CustomException exception) {
        log.error("❗❗️ exception = " + exception);
        return new ResponseEntity<>(new ResponseDto(exception.getCode(), exception.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
