package com.moira.itda.global.pagination.component

class PageSizeConstant {
    companion object {
        // 가챠정보 > 가챠목록 > 전체 목록 조회
        const val GACHA_LIST_PAGE_SIZE = 6

        // 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회
        const val GACHA_DETAIL_TRADE_PAGE_SIZE = 10

        // 판매 및 교환 대상 지정
        const val TRADE_TARGET_PAGE_SIZE = 3

        // 마이페이지 > 정보등록요청 결과
        const val MY_GACHA_ADD_SUGGEST_PAGE_SIZE = 5

        // 마이페이지 > 즐겨찾기 가챠목록 조회
        const val MY_WISH_GACHA_LIST_PAGE_SIZE = 6

        // 마이페이지 > 내 활동 > 판매/교환 목록 조회
        const val MY_TRADE_LIST_PAGE_SIZE = 10

        // 마이페이지 > 내 활동 > 제안 목록 조회
        const val MY_TRADE_SUGGEST_LIST_PAGE_SIZE = 10

        // 마이페이지 > 내 활동 > 채팅 목록 조회
        const val MY_TRADE_CHAT_LIST_PAGE_SIZE = 10

        // 제안목록 조회 모달 > 제안목록 조회
        const val TRADE_SUGGEST_MODAL_LIST_PAGE_SIZE = 5
    }
}