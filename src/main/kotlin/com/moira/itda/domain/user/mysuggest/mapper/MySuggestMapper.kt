package com.moira.itda.domain.user.mysuggest.mapper

import com.moira.itda.domain.user.mysuggest.dto.response.MySuggestResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MySuggestMapper {
    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 목록 조회 > 거래 목록 조회 > totalElements 계산
     */
    fun selectTradeListCnt(userId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 목록 조회 > 거래 목록 조회
     */
    fun selectTradeList(userId: String, pageSize: Int, offset: Int): List<MySuggestResponse>

    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 취소 > 구매 status 조회
     */
    fun selectPurchaseSuggestStatus(userId: String, suggestId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 취소 > 교환 status 조회
     */
    fun selectExchangeSuggestStatus(userId: String, suggestId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 취소 > 구매 status 변경
     */
    fun updatePurchaseSuggestStatusCanceled(userId: String, suggestId: String)

    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 취소 > 교환 status 변경
     */
    fun updateExchangeSuggestStatusCanceled(userId: String, suggestId: String)

}