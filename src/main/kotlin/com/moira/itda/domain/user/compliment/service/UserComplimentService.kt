package com.moira.itda.domain.user.compliment.service

import com.moira.itda.domain.user.compliment.dto.request.ComplimentRequest
import com.moira.itda.domain.user.compliment.mapper.UserComplimentMapper
import com.moira.itda.global.entity.TradeUserCompliment
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserComplimentService(
    private val mapper: UserComplimentMapper
) {
    /**
     * 유저칭찬
     */
    @Transactional
    fun compliment(userId: String, request: ComplimentRequest) {
        // [1] 유효성 검사 (특정 거래(TradeSuggest 기준)당 한 번의 칭찬만 가능)
        if (mapper.selectTradeUserComplimentChk(userId = userId, tradeSuggestId = request.tradeSuggestId)) {
            throw ItdaException(ErrorCode.ALREADY_COMPLIMENTED)
        }

        // [2] TradeUserCompliment 저장
        val tradeUserCompliment = TradeUserCompliment.from(userId = userId, request = request)
        mapper.insertTradeUserCompliment(tradeUserCompliment = tradeUserCompliment)
    }
}