package com.moira.itda.domain.exchange.service

import com.moira.itda.domain.exchange.dto.request.ExchangeAddRequest
import com.moira.itda.domain.exchange.dto.response.ExchangeItemResponse
import com.moira.itda.domain.exchange.dto.response.GachaIdResponse
import com.moira.itda.domain.exchange.mapper.ExchangeMapper
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ExchangeService(
    private val exchangeMapper: ExchangeMapper
) {
    /**
     * 교환등록 > 하위 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaItems(gachaId: String): List<ExchangeItemResponse> {
        return exchangeMapper.selectGachaItemList(gachaId = gachaId)
    }

    /**
     * 교환등록 > 유효성 검사
     */
    private fun validate(userId: String, gachaId: String, request: ExchangeAddRequest) {
        // 필드값 검증
        if (request.title.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_TRADE_TITLE)
        }
        if (request.content.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_TRADE_CONTENT)
        }
        if (request.fileId.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_TRADE_FILE_ID)
        }
        try {
            TradeHopeMethod.valueOf(request.hopeMethod)
        } catch (_: Exception) {
            throw ItdaException(ErrorCode.INVALID_TRADE_HOPE_METHOD)
        }
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        // 가챠 아이템 존재 여부 검증
        for (item in request.items) {
            if (exchangeMapper.selectGachaItemIdChk(gachaId = gachaId, gachaItemId = item.giveItemId) < 1) {
                throw ItdaException(ErrorCode.GACHA_ITEM_NOT_FOUND)
            }
            if (exchangeMapper.selectGachaItemIdChk(gachaId = gachaId, gachaItemId = item.wantItemId) < 1) {
                throw ItdaException(ErrorCode.GACHA_ITEM_NOT_FOUND)
            }
        }

        // 진행 중인 판매글이 있는지 검증
        if (exchangeMapper.selectExchangeChk(userId = userId, gachaId = gachaId) > 0) {
            throw ItdaException(ErrorCode.ALREADY_PENDING_SALES)
        }

        // 이미지 파일 검증
        if (exchangeMapper.selectImageFileCnt(fileId = request.fileId) < 1) {
            throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        }
    }

    /**
     * 교환등록
     */
    @Transactional
    fun exchange(userId: String, gachaId: String, request: ExchangeAddRequest): GachaIdResponse {
        // [1] 유효성 검사
        this.validate(userId = userId, gachaId = gachaId, request = request)

        // [2] 변수 세팅
        val tradeId = UUID.randomUUID().toString()

        // [3] Trade 저장
        val trade = Trade.fromExchangeAddRequest(
            userId = userId,
            gachaId = gachaId,
            tradeId = tradeId,
            request = request
        )
        exchangeMapper.insertTrade(trade = trade)

        // [4] TradeItem 저장
        request.items.forEach { item ->
            val tradeExchangeItem = TradeItem.fromExchangeItemAddRequest(
                tradeId = tradeId, gachaId = gachaId, request = item
            )
            exchangeMapper.insertTradeItem(tradeItem = tradeExchangeItem)
        }

        // [5] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }
}