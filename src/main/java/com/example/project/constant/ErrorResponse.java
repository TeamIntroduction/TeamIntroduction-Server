package com.example.project.constant;

import com.example.project.dto.ResponseStatus;
import lombok.Getter;

@Getter
public enum ErrorResponse {

    // 기본 ERROR
    BAD_REQUEST("ERR001", "잘못된 요청입니다"),
    NOT_FOUND("ERR002", "요청 경로 오류"),
    INTERNAL_ERROR("ERR003", "서버 내부 오류"),

    // user
    FAIL_LOGIN("USER001", "로그인 실패"),

    // member
    NOT_EXIST_ID("MEMBER001", "멤버 조회 실패"),

    // token
    TOKEN_ERROR("Token001", "토큰 에러"),
    EXPIRED_ACCESS_TOKEN("Token001", "만료된 Access 토큰입니다"),
    ;

    private final ResponseStatus status;
    private final String code;
    private final String message;

    ErrorResponse(String code, String message) {
        this.status = ResponseStatus.ERROR;
        this.code = code;
        this.message = message;
    }
}
