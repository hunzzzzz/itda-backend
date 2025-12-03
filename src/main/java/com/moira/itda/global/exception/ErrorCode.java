package com.moira.itda.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 시스템 관련 에러코드
    INTERNAL_SYSTEM_ERROR(
            "S0001",
            "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    // 유저 관련 에러코드
    ALREADY_USING_NICKNAME(
            "U0001",
            "이미 사용 중인 닉네임입니다.",
            HttpStatus.BAD_REQUEST
    ),
    ALREADY_USING_EMAIL(
            "U0002",
            "이미 사용 중인 이메일입니다.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_NAME(
            "U0003",
            "유효하지 않은 이름입니다.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_NICKNAME(
            "U0004",
            "유효하지 않은 이메일입니다.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_PHONE(
            "U0005",
            "유효하지 않은 휴대폰 번호입니다.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_EMAIL(
            "U0006",
            "유효하지 않은 이메일입니다.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_PASSWORD(
            "U0007",
            "유효하지 않은 비밀번호입니다.",
            HttpStatus.BAD_REQUEST
    );

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
