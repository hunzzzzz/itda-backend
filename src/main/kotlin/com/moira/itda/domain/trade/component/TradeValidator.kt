package com.moira.itda.domain.trade.component

import com.moira.itda.domain.common.mapper.CommonMapper
import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.dto.request.TradeAddRequest
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
     * 교환등록 > 유효성 검사 > 공통
     * 판매등록 > 유효성 검사 > 공통
     */
    fun validateTradeCommon(request: TradeAddRequest) {
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
        if (!commonMapper.selectFileIdChk(fileId = request.fileId)) {
            throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        }
        // 거래 희망 방식
        runCatching { TradeHopeMethod.valueOf(request.hopeMethod) }
            .onFailure { throw ItdaException(ErrorCode.INVALID_TRADE_HOPE_METHOD) }
    }

    /**
     * 교환등록 > 유효성 검사
     */
    fun validateExchange(userId: String, gachaId: String, request: ExchangeAddRequest) {
        // 공통 유효성 검사
        this.validateTradeCommon(request = request)

        // 교환 하위 아이템
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        // 진행 중인 교환글 존재 여부 검증
        if (mapper.selectTradeExchangeChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_EXCHANGE_EXISTS)
        }
    }

    /**
     * 판매등록 > 유효성 검사
     */
    fun validateSales(userId: String, gachaId: String, request: SalesAddRequest) {
        // 공통 유효성 검사
        this.validateTradeCommon(request = request)

        // 판매 하위 아이템
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        for (item in request.items) {
            // 아이템 수량
            if (item.count < 1) {
                throw ItdaException(ErrorCode.INVALID_TRADE_ITEM_COUNT)
            }
            // 아이템 가격
            if (item.price < 1) {
                throw ItdaException(ErrorCode.INVALID_TRADE_ITEM_PRICE)
            }
        }

        // 진행 중인 판매글 존재 여부 검증
        if (mapper.selectTradeSalesChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_SALES_EXISTS)
        }
    }
}