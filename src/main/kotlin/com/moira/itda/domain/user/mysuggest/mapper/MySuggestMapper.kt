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
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 취소 > status 조회
     */
    fun selectSuggestStatus(userId: String, suggestId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 취소
     */
    fun updateSuggestStatusCanceled(userId: String, suggestId: String)
}