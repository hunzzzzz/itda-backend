package com.moira.itda.domain.user.mysuggest.mapper

import com.moira.itda.domain.user.mysuggest.dto.response.MySuggestResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MySuggestMapper {
    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 목록 조회 > totalElements 계산
     */
    fun selectTradeSuggestListCnt(userId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 목록 조회
     */
    fun selectTradeSuggestList(userId: String, pageSize: Int, offset: Int): List<MySuggestResponse>

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 취소 > status 조회
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 삭제 > status 조회
     */
    fun selectTradeSuggestStatus(userId: String, suggestId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 취소 > status 변경 (CANCELED)
     */
    fun updateTradeSuggestStatusCanceled(userId: String, suggestId: String)

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 삭제 > TradeSuggest 삭제
     */
    fun deleteTradeSuggest(userId: String, suggestId: String)
}