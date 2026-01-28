package com.moira.itda.domain.trade.add.component

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.trade.add.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.add.dto.request.TradeRequest
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class TradeValidator(
    private val mapper: com.moira.itda.domain.trade.add.mapper.TradeMapper,
    private val commonImageMapper: CommonImageMapper
) {
    /**
     * 교환등록 > 유효성 검사 > 공통
     * 판매등록 > 유효성 검사 > 공통
     */
    private fun validateRequestData(
        request: TradeRequest,
        fileIdCheck: Boolean = true
    ) {
        // 거래 제목
        if (request.title.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_TITLE)
        }
        // 거래 파일 ID
        if (fileIdCheck) {
            if (request.fileId.isBlank()) {
                throw ItdaException(ErrorCode.NO_TRADE_FILE_ID)
            }
            if (!commonImageMapper.selectFileIdChk(fileId = request.fileId)) {
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
    fun validateExchangeAdd(request: com.moira.itda.domain.trade.add.dto.request.ExchangeAddRequest) {
        // [1] 사용자 입력값에 대한 공통 유효성 검사
        this.validateRequestData(request = request)

        // [2] 교환하고자 하는 하위 아이템이 최소 1개 이상이어야 한다.
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        // [3] 같은 아이템끼리 교환할 수 없다.
        request.items.forEach { item ->
            if (item.giveItemId == item.wantItemId) {
                throw ItdaException(ErrorCode.SAME_EXCHANGE_ITEMS)
            }
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

    /**
     * 거래삭제 > 유효성 검사 > 공통 (권한)
     * 거래삭제 > 유효성 검사 > 공통 (권한)
     */
    private fun validateRole(userId: String, tradeUserId: String) {
        // 수정 및 삭제 권한 검증
        if (tradeUserId != userId) {
            throw ItdaException(ErrorCode.OTHERS_TRADE)
        }
    }

    /**
     * 거래수정 > 유효성 검사 > 공통 (상태값)
     * 거래삭제 > 유효성 검사 > 공통 (상태값)
     */
    private fun validateStatus(tradeItemId: String) {
        // APPROVED된 제안이 존재하는 경우 삭제 불가능
        if (mapper.selectTradeSuggestApprovedChk(tradeItemId = tradeItemId)) {
            throw ItdaException(ErrorCode.APPROVED_SUGGEST_EXISTS)
        }

        // PENDING된 제안이 존재하는 경우 삭제 불가능
        if (mapper.selectTradeSuggestPendingChk(tradeItemId = tradeItemId)) {
            throw ItdaException(ErrorCode.PENDING_SUGGEST_EXISTS)
        }
    }

    /**
     * 거래수정 > 유효성 검사 (교환)
     */
    fun validateUpdate(
        userId: String,
        tradeUserId: String,
        request: com.moira.itda.domain.trade.add.dto.request.ExchangeUpdateRequest
    ) {
        // [1] 권한 검증
        this.validateRole(userId = userId, tradeUserId = tradeUserId)

        // [2] 상태값 검증
        if (request.deleteItems != null && request.deleteItems.isNotEmpty()) {
            request.deleteItems.forEach { tradeItemId ->
                this.validateStatus(tradeItemId = tradeItemId)
            }
        }

        // [3] 사용자 입력값 검증
        this.validateRequestData(request = request, fileIdCheck = request.imageChangeYn == "Y")

        // [4] 같은 아이템끼리 교환할 수 없다.
        if (request.updateItems != null && request.updateItems.isNotEmpty()) {
            request.updateItems.forEach { item ->
                if (item.giveItemId == item.wantItemId) {
                    throw ItdaException(ErrorCode.SAME_EXCHANGE_ITEMS)
                }
            }
        }
        if (request.newItems != null && request.newItems.isNotEmpty()) {
            request.newItems.forEach { item ->
                if (item.giveItemId == item.wantItemId) {
                    throw ItdaException(ErrorCode.SAME_EXCHANGE_ITEMS)
                }
            }
        }
    }

    /**
     * 거래수정 > 유효성 검사 (판매)
     */
    fun validateUpdate(
        userId: String,
        tradeUserId: String,
        request: com.moira.itda.domain.trade.add.dto.request.SalesUpdateRequest
    ) {
        // [1] 권한 검증
        this.validateRole(userId = userId, tradeUserId = tradeUserId)

        // [2] 상태값 검증
        if (request.deleteItems != null && request.deleteItems.isNotEmpty()) {
            request.deleteItems.forEach { tradeItemId ->
                this.validateStatus(tradeItemId = tradeItemId)
            }
        }

        // [3] 사용자 입력값 검증
        this.validateRequestData(request = request, fileIdCheck = request.imageChangeYn == "Y")

        // [4] 판매 가격 유효성 검사
        if (request.updateItems != null && request.updateItems.isNotEmpty()) {
            request.updateItems.forEach { item ->
                if (item.price < 1 || item.price % 10 != 0) {
                    throw ItdaException(ErrorCode.INVALID_TRADE_PRICE)
                }
            }
        }
        if (request.newItems != null && request.newItems.isNotEmpty()) {
            request.newItems.forEach { item ->
                if (item.price < 1 || item.price % 10 != 0) {
                    throw ItdaException(ErrorCode.INVALID_TRADE_PRICE)
                }
            }
        }
    }

    /**
     * 거래삭제 > 유효성 검사
     */
    fun validateDelete(userId: String, tradeUserId: String, tradeItemId: String) {
        // [1] 권한 검증
        this.validateRole(userId = userId, tradeUserId = tradeUserId)

        // [2] 상태값 검증
        this.validateStatus(tradeItemId = tradeItemId)
    }
}