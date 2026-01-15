package com.moira.itda.domain.user_activity_completed_trade.mapper

import com.moira.itda.domain.user_activity_completed_trade.dto.response.CompletedTradeResponse
import com.moira.itda.global.entity.TradeUserCompliment
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserActivityCompletedTradeMapper {
    /**
     * 완료된 거래목록 조회 > totalElements 계산
     */
    fun selectCompletedTradeListCnt(userId: String): Long

    /**
     * 완료된 거래목록 조회
     */
    fun selectCompletedTradeList(userId: String): List<CompletedTradeResponse>

    /**
     * 유저칭찬 > 칭찬 여부 조회 (TradeSuggest 기준)
     */
    fun selectTradeUserComplimentChk(userId: String, tradeSuggestId: String): Boolean

    /**
     * 유저칭찬 > TradeUserCompliment 저장
     */
    fun insertTradeUserCompliment(tradeUserCompliment: TradeUserCompliment)
}