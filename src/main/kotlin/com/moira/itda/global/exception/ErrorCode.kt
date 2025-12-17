package com.moira.itda.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus
) {
    // 시스템 관련 에러코드
    INTERNAL_SERVER_ERROR(
        code = "INTERNAL_SERVER_ERROR",
        message = "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),

    // 유저 관련 에러코드
    ALREADY_USING_NICKNAME(
        code = "U0001",
        message = "이미 사용 중인 닉네임입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    ALREADY_USING_EMAIL(
        code = "U0002",
        message = "이미 사용 중인 이메일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_EMAIL(
        code = "U0003",
        message = "유효하지 않은 이메일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_PASSWORD(
        code = "U0004",
        message = "유효하지 않은 비밀번호입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_NAME(
        code = "U0005",
        message = "유효하지 않은 이름입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_NICKNAME(
        code = "U0006",
        message = "유효하지 않은 닉네임입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    LOGIN_ERROR(
        code = "U0007",
        message = "이메일 혹은 비밀번호를 잘못 입력하였습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    USER_NOT_FOUND(
        code = "U0010",
        message = "존재하지 않는 유저입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    PASSWORD_NOT_MATCH(
        code = "U0011",
        message = "비밀번호가 일치하지 않습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    CANNOT_UPDATE_WITH_SAME_PASSWORD(
        code = "U0012",
        message = "동일한 비밀번호로 변경할 수 업습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    // 권한 관련 에러코드
    INVALID_AUTHORIZATION_HEADER(
        code = "A0001",
        message = "Authorization 헤더에 토큰 정보가 포함되어 있지 않습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_TOKEN(
        code = "A0002",
        message = "유효하지 않은 토큰입니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),
    EXPIRED_ATK(
        code = "A0003",
        message = "AccessToken이 만료되었습니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),
    INVALID_SIGNATURE(
        code = "A0004",
        message = "토큰 서명이 유효하지 않거나 형식이 올바르지 않습니다.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),
    EXPIRED_USER_INFO(
        code = "A0005",
        message = "사용자의 로그인 정보가 만료되었습니다. 다시 로그인해주세요.",
        httpStatus = HttpStatus.UNAUTHORIZED
    ),

    // 파일 관련 에러코드
    INVALID_FILE(
        code = "F0001",
        message = "유효하지 않은 파일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    FILE_SAVE_FAILED(
        code = "F0002",
        message = "파일 저장에 실패하였습니다.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    FILE_DELETE_FAILED(
        code = "F0003",
        message = "파일 삭제에 실패하였습니다.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    FILE_SIZE_LIMIT_EXCEEDED(
        code = "F0004",
        message = "최대 5MB 크기의 파일만 첨부할 수 있습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    UNSUPPORTED_FILE_EXTENSION(
        code = "F0005",
        message = "지원하지 않는 파일 확장자입니다. (허용 확장자: jpg, jpeg, png)",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    FILE_NOT_FOUND(
        code = "F0006",
        message = "존재하지 않는 파일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    // 가챠 관련 에러코드
    INVALID_GACHA_TITLE(
        code = "G0001",
        message = "유효하지 않은 가챠 제목입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_GACHA_PRICE(
        code = "G0002",
        message = "유효하지 않은 가챠 가격입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_GACHA_FILE_ID(
        code = "G0003",
        message = "유효하지 않은 가챠 파일 정보입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    NO_GACHA_ITEMS(
        code = "G0004",
        message = "가챠 아이템 정보가 없습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_GACHA_ITEM_NAME(
        code = "G0005",
        message = "유효하지 않은 가챠 아이템 이름입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    GACHA_EXCEEDED_MAX_FILE_COUNT(
        code = "G0006",
        message = "최대 1개의 파일만 업로드할 수 있습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    GACHA_NOT_FOUND(
        code = "G0007",
        message = "존재하지 않는 가챠입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
}