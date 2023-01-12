package com.example.project.constant;

import com.example.project.dto.ResponseStatus;
import lombok.Getter;

@Getter
public enum SuccessResponse {

    // key
    GENERATE_ASYMMETRIC_KEY("KEY001", "키 생성 완료"),
    STORE_SYMMETRIC_KEY("KEY001", "대칭키 저장 완료"),

    // member
    GET_MEMBER_LIST("MEMBER001", "멤버 리스트 조회 완료"),
    GET_MEMBER("MEMBER002", "멤버 조회 완료"),

    // team
    GET_TEAMS("TEAM001", "부서 리스트 조회 완료"),

    // user
    LOGIN("USER001", "로그인 완료"),

    // token
    REISSUE_TOKEN("TOKEN001", "토큰 재발행 완료")
    ;

    private final ResponseStatus status;
    private final String code;
    private final String message;

    SuccessResponse(String code, String message) {
        this.status = ResponseStatus.SUCCESS;
        this.code = code;
        this.message = message;
    }
}
