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
    ),
    LOGIN_ERROR(
            "U0008",
            "이메일 혹은 비밀번호를 잘못 입력하였습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 권한 관련 에러코드
    INVALID_AUTHORIZATION_HEADER(
            "A0001",
            "Authorization 헤더에 토큰 정보가 포함되어 있지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_TOKEN(
            "A0002",
            "유효하지 않은 토큰입니다.",
            HttpStatus.UNAUTHORIZED
    ),
    EXPIRED_ATK(
            "A0003",
            "AccessToken이 만료되었습니다.",
            HttpStatus.UNAUTHORIZED
    ),
    INVALID_SIGNATURE(
            "A0004",
            "토큰 서명이 유효하지 않거나 형식이 올바르지 않습니다.",
            HttpStatus.UNAUTHORIZED
    ),
    ACCESS_DENIED(
            "A0005",
            "접근 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),

    // 가챠 관련 에러코드
    INVALID_TITLE(
            "G0001",
            "유효하지 않은 가챠 제목입니다.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_PRICE(
            "G0002",
            "유효하지 않은 가챠 가격입니다.",
            HttpStatus.BAD_REQUEST
    ),
    NO_FILE_ID(
            "G0003",
            "이미지 파일 정보가 없습니다.",
            HttpStatus.BAD_REQUEST
    ),
    NO_GACHA_ITEMS(
            "G0004",
            "가챠 하위 항목 정보가 없습니다.",
            HttpStatus.BAD_REQUEST
    ),
    SERIES_EXCEEDED_MAX_FILE_COUNT(
            "G0005",
            "최대 1개의 파일만 업로드할 수 있습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 파일 관련 에러코드
    FILE_NOT_FOUND(
            "F0001",
            "존재하지 않는 파일입니다.",
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
