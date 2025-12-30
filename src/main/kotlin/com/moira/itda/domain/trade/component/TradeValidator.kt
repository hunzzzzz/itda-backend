package com.moira.itda.domain.trade.component

import com.moira.itda.domain.common.mapper.CommonMapper
import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.dto.request.ExchangeUpdateRequest
import com.moira.itda.domain.trade.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.dto.request.TradeRequest
import com.moira.itda.domain.trade.mapper.TradeMapper
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.entity.TradeStatus
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
    fun validateExchangeExists(userId: String, gachaId: String) {
        if (mapper.selectTradeExchangeChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_EXCHANGE_EXISTS)
        }
    }

    /**
     * 진행 중인 판매글 존재 여부 확인
     */
    fun validateSalesExists(userId: String, gachaId: String) {
        if (mapper.selectTradeSalesChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_SALES_EXISTS)
        }
    }

    /**
     * 교환등록 > 유효성 검사 > 공통
     * 판매등록 > 유효성 검사 > 공통
     * 교환 수정 > 유효성 검사 > 공통
     */
    private fun validateRequestData(request: TradeRequest, fileIdCheck: Boolean = true) {
        // 거래 제목
        if (request.title.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_TITLE)
        }
        // 거래 내용
        if (request.content.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_CONTENT)
        }
        // 거래 파일 ID
        if (fileIdCheck) {
            if (request.fileId.isBlank()) {
                throw ItdaException(ErrorCode.NO_TRADE_FILE_ID)
            }
            if (!commonMapper.selectFileIdChk(fileId = request.fileId)) {
                throw ItdaException(ErrorCode.FILE_NOT_FOUND)
            }
        }
        // 거래 희망 방식
        runCatching { TradeHopeMethod.valueOf(request.hopeMethod) }
            .onFailure { throw ItdaException(ErrorCode.INVALID_TRADE_HOPE_METHOD) }
    }

    /**
     * 교환수정 > 유효성 검사 > 공통
     * 거래 삭제 > 유효성 검사 > 공통
     */
    private fun validateStatusAndRole(userId: String, trade: Trade) {
        // [1]
        if (trade.status == TradeStatus.COMPLETED) {
            throw ItdaException(ErrorCode.COMPLETED_TRADE)
        }

        // [2] 삭제 권한에 대한 유효성 검사 (업로드한 유저 = 요청 유저인지)
        if (trade.userId != userId) {
            throw ItdaException(ErrorCode.OTHERS_TRADE)
        }

        // [3] 제안 목록에 대한 유효성 검사 (APPROVED된 제안이 있는지)
        if (mapper.selectTradeSuggestApprovedChk(tradeId = trade.id)) {
            throw ItdaException(ErrorCode.APPROVED_SUGGEST_EXISTS)
        }
    }

    /**
     * 교환등록 > 유효성 검사
     */
    fun validateExchange(userId: String, gachaId: String, request: ExchangeAddRequest) {
        // [1] 사용자 입력값에 대한 유효성 검사
        this.validateRequestData(request = request)

        // [2] 교환 하위 아이템 목록 존재 여부 검증
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        // [3] 진행 중인 교환글 존재 여부 검증
        if (mapper.selectTradeExchangeChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_EXCHANGE_EXISTS)
        }
    }

    /**
     * 판매등록 > 유효성 검사
     */
    fun validateSales(userId: String, gachaId: String, request: SalesAddRequest) {
        // [1] 사용자 입력값에 대한 유효성 검사
        this.validateRequestData(request = request)

        // [2] 판매 하위 아이템 목록 존재 여부 검증
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        // [3] 판매 하위 아이템에 대한 수량, 가격 검증
        for (item in request.items) {
            // 아이템 수량
            if (item.count < 1) {
                throw ItdaException(ErrorCode.INVALID_TRADE_COUNT)
            }
            // 아이템 가격
            if (item.price < 1) {
                throw ItdaException(ErrorCode.INVALID_TRADE_PRICE)
            }
        }

        // [4] 진행 중인 판매글 존재 여부 검증
        if (mapper.selectTradeSalesChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_SALES_EXISTS)
        }
    }

    /**
     * 교환 수정 > 유효성 검사
     */
    fun validateExchange(userId: String, gachaId: String, trade: Trade, request: ExchangeUpdateRequest) {
        // [1] 사용자 입력값에 대한 유효성 검사
        this.validateRequestData(request = request, fileIdCheck = request.imageChangeYn == "Y")

        // [2] 교환 하위 아이템 목록 존재 여부 검증
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        // [3] 진행 중인 교환글 존재 여부 검증
        if (mapper.selectTradeExchangeChk(userId = userId, gachaId = gachaId)) {
            throw ItdaException(ErrorCode.PENDING_EXCHANGE_EXISTS)
        }

        // [4] 상태값 및 권한 검증
        this.validateStatusAndRole(userId = userId, trade = trade)
    }

    /**
     * 거래 삭제 > 유효성 검사
     */
    fun validateDeleteTrade(userId: String, trade: Trade) {
        // [1] 권한 및 상태값 검증
        this.validateStatusAndRole(userId = userId, trade = trade)
    }
}