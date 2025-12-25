package com.moira.itda.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    var message: String,
    val httpStatus: HttpStatus
) {
    // 공통 에러코드
    INTERNAL_SERVER_ERROR(
        code = "C0001",
        message = "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    INVALID_SORT_CONDITION(
        code = "C0002",
        message = "올바르지 않은 정렬 조건입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
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
    INVALID_BAN_REASON(
        code = "U0013",
        message = "계정 정지 사유는 필수 입력 항목입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_BAN_DAYS(
        code = "U0014",
        message = "유효하지 않은 계정 정지일수입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    BANNED_USER_CANNOT_LOGIN(
        code = "U0015",
        message = "%s까지 계정이 정지된 유저입니다.",
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
    ACCESS_DENIED(
        code = "A0006",
        message = "접근 권한이 없습니다.",
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

    // 공통코드 관련 에러코드
    INVALID_CODE_KEY(
        code = "C0001",
        message = "유효하지 않은 코드 키입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    ALREADY_USING_KEY(
        code = "C0002",
        message = "이미 사용중인 코드 키입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    ALREADY_USING_CODE_DETAIL_NAME(
        code = "C0003",
        message = "이미 사용중인 코드 이름입니다.",
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
    ALREADY_PENDING_SALES(
        code = "G0008",
        message = "이미 해당 가챠 관련 상품을 판매중입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    ALREADY_PENDING_EXCHANGE(
        code = "G0009",
        message = "이미 해당 가챠 관련 상품을 교환중입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    GACHA_ITEM_NOT_FOUND(
        code = "G0010",
        message = "존재하지 않는 가챠 아이템입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    // 거래 관련 에러코드
    INVALID_TRADE_TITLE(
        code = "T0001",
        message = "유효하지 않은 거래 제목입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_TRADE_CONTENT(
        code = "T0002",
        message = "유효하지 않은 거래 내용입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_TRADE_FILE_ID(
        code = "T0003",
        message = "유효하지 않은 거래 이미지 파일 정보입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_TRADE_HOPE_METHOD(
        code = "T0004",
        message = "유효하지 않은 거래 희망 방법입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    NO_TRADE_ITEMS(
        code = "T0005",
        message = "거래 아이템 정보가 없습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_TRADE_ITEM_COUNT(
        code = "T0006",
        message = "유효하지 않은 거래 수량입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_TRADE_ITEM_PRICE(
        code = "T0007",
        message = "유효하지 않은 거래 가격입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    CANNOT_LOAD_TRADE_LIST(
        code = "T0008",
        message = "거래 목록을 불러올 수 없습니다. 잠시 후 다시 시도해주세요.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
    INVALID_TRADE_TYPE(
        code = "T0009",
        message = "유효하지 않은 거래 종류입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    TRADE_COUNT_SHOULD_BE_LARGER_THAN_ZERO(
        code = "T0010",
        message = "거래 수량은 0보다 커야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    SHOULD_TYPE_PRICE_WHEN_YOU_WANT_NEGOTIATION(
        code = "T0011",
        message = "네고 희망을 체크한 경우, 네고 희망 가격을 입력해야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    DISCOUNT_PRICE_SHOULD_BE_LESS_THAN_ORIGINAL_PRICE(
        code = "T0012",
        message = "네고 희망 가격은 원래 가격보다 작아야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    SUGGEST_NOT_FOUND(
        code = "T0013",
        message = "존재하지 않는 거래 제안입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    CANNOT_CANCEL_APPROVED_SUGGEST(
        code = "T0014",
        message = "이미 승인된 거래 제안은 취소할 수 없습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    CANNOT_CANCEL_REJECTED_SUGGEST(
        code = "T0015",
        message = "이미 거절된 거래 제안은 취소할 수 없습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    ALREADY_CANCELED_SUGGEST(
        code = "T0016",
        message = "이미 취소된 거래 제안입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    EXCHANGING_ITEMS_ID_SHOULD_NOT_BE_SAME_WHEN_YOU_WANT_NEGOTIATION(
        code = "T0017",
        message = "'다른 아이템으로 교환 제시'를 선택한 경우, 기존 항목과 다른 아이템을 선택해야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    SUGGEST_ONLY_WHEN_TRADE_IS_PENDING(
        code = "T0018",
        message = "진행 중인 거래에 대해서만 교환 및 판매 제안이 가능합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    ALREADY_SUGGESTED_PURCHASE_ON_THE_TRADE_ITEM(
        code = "T0019",
        message = "이미 해당 제품에 구매 제안을 하였습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ), 
    ALREADY_SUGGESTED_EXCHANGE_ON_THE_TRADE_ITEM(
        code = "T0020",
        message = "이미 해당 제품에 교환 제안을 하였습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_SUGGEST_TYPE(
        code = "T0021",
        message = "유효하지 않은 거래 타입입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    TRADE_NOT_FOUND(
        code = "T0022",
        message = "존재하지 않는 거래입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    CANNOT_DELETE_TRADE_WHEN_STATUS_IS_COMPLETED(
        code = "T0023",
        message = "이미 완료된 거래는 삭제할 수 없습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    CANNOT_DELETE_TRADE_OF_OTHERS(
        code = "T0024",
        message = "다른 유저가 업로드한 거래를 삭제할 수 없습니다.",
        httpStatus = HttpStatus.FORBIDDEN
    ),
    CANNOT_DELETE_TRADE_WHEN_APPROVED_SUGGEST_EXISTS(
        code = "T0025",
        message = "이미 승인된 거래 제안이 있어 삭제할 수 없습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    // 정보 등록/수정 요청 관련 에러코드
    INVALID_GACHA_INFO_TYPE(
        code = "I0001",
        message = "유효하지 않은 요청 타입입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    GACHA_INFO_CONTENT_IS_MANDATORY(
        code = "I0002",
        message = "요청 내용은 필수 입력 항목입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_GACHA_INFO_MESSAGE_YN(
        code = "I0003",
        message = "유효하지 않은 메시지 수신 여부입니다. (Y/N)",
        httpStatus = HttpStatus.BAD_REQUEST
    )

}