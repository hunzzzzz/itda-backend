package com.moira.itda.domain.user_completed_trade.mapper

import com.moira.itda.domain.user_completed_trade.dto.response.CompletedTradeResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserCompletedTradeMapper {
    /**
     * 완료된 거래목록 조회 > totalElements 계산
     */
    fun selectCompletedTradeListCnt(userId: String): Long

    /**
     * 완료된 거래목록 조회
     */
    fun selectCompletedTradeList(userId: String): List<CompletedTradeResponse>
}