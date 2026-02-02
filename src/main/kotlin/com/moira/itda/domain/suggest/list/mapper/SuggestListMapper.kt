package com.moira.itda.domain.suggest.list.mapper

import com.moira.itda.domain.suggest.list.dto.response.SuggestListResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestListMapper {
    /**
     * 제안목록 조회 > totalElements 계산
     */
    fun selectTradeSuggestListCnt(tradeId: String): Long

    /**
     * 제안목록 조회
     */
    fun selectTradeSuggestList(
        userId: String,
        tradeId: String,
        pageSize: Int,
        offset: Int
    ): List<SuggestListResponse>

    /**
     * 내 제안목록 조회 > totalElements 계산
     */
    fun selectMyTradeSuggestListCnt(userId: String): Long

    /**
     * 내 제안목록 조회
     */
    fun selectMyTradeSuggestList(
        userId: String,
        pageSize: Int,
        offset: Int
    ): List<SuggestListResponse>
}