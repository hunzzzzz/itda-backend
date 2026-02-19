package com.moira.itda.domain.user.compliment.mapper

import com.moira.itda.global.entity.TradeUserCompliment
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserComplimentMapper {
    /**
     * 유저칭찬 > 칭찬 여부 조회 (TradeSuggest 기준)
     */
    fun selectTradeUserComplimentChk(userId: String, tradeSuggestId: String): Boolean

    /**
     * 유저칭찬 > TradeUserCompliment 저장
     */
    fun insertTradeUserCompliment(tradeUserCompliment: TradeUserCompliment)
}