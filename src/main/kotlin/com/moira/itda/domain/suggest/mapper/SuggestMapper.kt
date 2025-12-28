package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.TradeSuggestResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestMapper {
    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 목록 조회 > totalElements 계산
     */
    fun selectTradeSuggestListCnt(tradeId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회
     */
    fun selectTradeSuggestList(tradeId: String, pageSize: Int, offset: Int): List<TradeSuggestResponse>
}