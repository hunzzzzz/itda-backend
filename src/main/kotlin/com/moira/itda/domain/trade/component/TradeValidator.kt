package com.moira.itda.domain.trade.component

import com.moira.itda.domain.common.mapper.CommonMapper
import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.dto.request.TradeRequest
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
     * 교환등록 > 유효성 검사 > 공통
     * 판매등록 > 유효성 검사 > 공통
     */
    private fun validateRequestData(request: TradeRequest, fileIdCheck: Boolean = true) {
        // 거래 제목
        if (request.title.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_TITLE)
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

        // TODO: 직거래 선택 시, 좌표 검증 로직 필요
    }

    /**
     * 교환등록 > 유효성 검사
     */
    fun validateExchangeAdd(request: ExchangeAddRequest) {
        // [1] 사용자 입력값에 대한 공통 유효성 검사
        this.validateRequestData(request = request)

        // [2] 교환하고자 하는 하위 아이템이 최소 1개 이상이어야 한다.
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }
    }

    /**
     * 판매등록 > 유효성 검사
     */
    fun validateSalesAdd(request: SalesAddRequest) {
        // [1] 사용자 입력값에 대한 공통 유효성 검사
        this.validateRequestData(request = request)

        // [2] 판매하고자 하는 하위 아이템이 최소 1개 이상이어야 한다.
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        // [3] 판매 가격 유효성 검사
        request.items.forEach { item ->
            if (item.price < 1 || item.price % 10 != 0) {
                throw ItdaException(ErrorCode.INVALID_TRADE_PRICE)
            }
        }
    }

//    /**
//     * 교환 수정 > 유효성 검사
//     */
//    fun validateExchangeUpdate(userId: String, trade: Trade, request: ExchangeUpdateRequest) {
//        // [1] 사용자 입력값에 대한 유효성 검사
//        this.validateRequestData(request = request, fileIdCheck = request.imageChangeYn == "Y")
//
//        // [2] 교환 하위 아이템 목록 존재 여부 검증
//        if (request.items.isEmpty()) {
//            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
//        }
//
//        // [3] 상태값 및 권한 검증
//        this.validateStatusAndRole(userId = userId, trade = trade)
//    }
//
//    /**
//     * 판매 수정 > 유효성 검사
//     */
//    fun validateSalesUpdate(userId: String, trade: Trade, request: SalesUpdateRequest) {
//        // [1] 사용자 입력값에 대한 유효성 검사
//        this.validateRequestData(request = request, fileIdCheck = request.imageChangeYn == "Y")
//
//        // [2] 교환 하위 아이템 목록 존재 여부 검증
//        if (request.items.isEmpty()) {
//            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
//        }
//
//        // [3] 상태값 및 권한 검증
//        this.validateStatusAndRole(userId = userId, trade = trade)
//    }
//
    /**
     * 거래삭제 > 유효성 검사 > 공통 (권한)
     */
    private fun validateRole(userId: String, tradeUserId: String) {
        // 수정 및 삭제 권한 검증
        if (tradeUserId != userId) {
            throw ItdaException(ErrorCode.OTHERS_TRADE)
        }
    }

    /**
     * 거래삭제 > 유효성 검사 > 공통 (상태값)
     */
    private fun validateStatus(tradeItemId: String) {
        // APPROVED된 제안이 존재하는 경우 거래수정 및 삭제 불가능
        if (mapper.selectTradeSuggestApprovedChk(tradeItemId = tradeItemId)) {
            throw ItdaException(ErrorCode.APPROVED_SUGGEST_EXISTS)
        }

        // PENDING된 제안이 존재하는 경우 거래수정 및 삭제 불가능
        if (mapper.selectTradeSuggestPendingChk(tradeItemId = tradeItemId)) {
            throw ItdaException(ErrorCode.PENDING_SUGGEST_EXISTS)
        }
    }

    /**
     * 거래삭제 > 유효성 검사
     */
    fun validateDeleteTrade(userId: String, tradeUserId: String, tradeItemId: String) {
        // [1] 권한 검증
        this.validateRole(userId = userId, tradeUserId = tradeUserId)

        // [2] 상태값 검증
        this.validateStatus(tradeItemId = tradeItemId)
    }
}