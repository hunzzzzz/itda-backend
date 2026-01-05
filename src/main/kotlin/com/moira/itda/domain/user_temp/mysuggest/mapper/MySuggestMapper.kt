package com.moira.itda.domain.user_temp.mysuggest.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface MySuggestMapper {
    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 취소 > status 조회
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 삭제 > status 조회
     */
    fun selectTradeSuggestStatus(userId: String, suggestId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 취소 > status 변경 (CANCELED)
     */
    fun updateTradeSuggestStatusCanceled(userId: String, suggestId: String)

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 삭제 > TradeSuggest 삭제
     */
    fun deleteTradeSuggest(userId: String, suggestId: String)
}