package com.moira.itda.domain.trade.component

import com.moira.itda.domain.common.mapper.CommonMapper
import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.mapper.TradeMapper
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class TradeValidator(
    private val mapper: TradeMapper,
    private val commonMapper: CommonMapper
) {
    /**
     * 진행 중인 교환글 존재 여부 확인
     */
    fun validateTradeExchange(userId: String, gachaId: String) {
        if (mapper.selectTradeExchangeChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_EXCHANGE_EXISTS)
        }
    }

    /**
     * 진행 중인 판매글 존재 여부 확인
     */
    fun validateTradeSales(userId: String, gachaId: String) {
        if (mapper.selectTradeSalesChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_SALES_EXISTS)
        }
    }

    /**
     * 교환등록 > 유효성 검사
     */
    fun validateExchange(userId: String, gachaId: String, request: ExchangeAddRequest) {
        // 거래 제목
        if (request.title.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_TITLE)
        }
        // 거래 내용
        if (request.content.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_CONTENT)
        }
        // 거래 파일 ID
        if (request.fileId.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_FILE_ID)
        }
        if (commonMapper.selectFileIdChk(fileId = request.fileId) < 1) {
            throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        }
        // 거래 희망 방식
        try {
            TradeHopeMethod.valueOf(request.hopeMethod)
        } catch (_: Exception) {
            throw ItdaException(ErrorCode.INVALID_TRADE_HOPE_METHOD)
        }
        // 거래 아이템
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }
        // 진행 중인 교환글 존재 여부 검증
        if (mapper.selectTradeExchangeChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_EXCHANGE_EXISTS)
        }
    }
}