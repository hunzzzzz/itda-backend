package com.moira.itda.domain.trade.service

import com.moira.itda.domain.trade.component.TradeValidator
import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.dto.response.GachaIdResponse
import com.moira.itda.domain.trade.mapper.TradeMapper
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.entity.TradeType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeService(
    private val mapper: TradeMapper,
    private val validator: TradeValidator
) {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 교환 > 진행 중인 교환글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkTradeExchange(userId: String, gachaId: String) {
        // [1] 유효성 검사
        validator.validateTradeExchange(userId = userId, gachaId = gachaId)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 판매 > 진행 중인 판매글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkTradeSales(userId: String, gachaId: String) {
        // [1] 유효성 검사
        validator.validateTradeSales(userId = userId, gachaId = gachaId)
    }

    /**
     * 교환등록
     */
    @Transactional
    fun exchange(userId: String, gachaId: String, request: ExchangeAddRequest): GachaIdResponse {
        // [1] 유효성 검사
        validator.validateExchange(userId = userId, gachaId = gachaId, request = request)

        // [2] Trade 저장
        val trade = Trade.fromRequest(type = TradeType.EXCHANGE, userId = userId, gachaId = gachaId, request = request)
        mapper.insertTrade(trade = trade)

        // [3] TradeItem 저장
        request.items.forEach { item ->
            val tradeExchangeItem = TradeItem.fromRequest(
                tradeId = trade.id, gachaId = gachaId, request = item
            )
            mapper.insertTradeItem(tradeItem = tradeExchangeItem)
        }

        // [4] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }

    /**
     * 판매등록
     */
    @Transactional
    fun sale(userId: String, gachaId: String, request: SalesAddRequest): GachaIdResponse {
        // [1] 유효성 검사
        validator.validateSales(userId = userId, gachaId = gachaId, request = request)

        // [2] Trade 저장
        val trade = Trade.fromRequest(type = TradeType.SALES, userId = userId, gachaId = gachaId, request = request)
        mapper.insertTrade(trade = trade)

        // [3] TradeItem 저장
        request.items.forEach { item ->
            val tradeSalesItem = TradeItem.fromRequest(
                gachaId = gachaId, tradeId = trade.id, request = item
            )
            mapper.insertTradeItem(tradeItem = tradeSalesItem)
        }

        // [4] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }
}