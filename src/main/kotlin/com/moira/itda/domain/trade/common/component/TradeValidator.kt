package com.moira.itda.domain.trade.common.component

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.trade.common.mapper.TradeCommonMapper
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.entity.TradeStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class TradeValidator(
    private val commonImageMapper: CommonImageMapper,
    private val commonTradeMapper: TradeCommonMapper
) {
    /**
     * 권한 검증 (수정, 삭제 시)
     */
    fun validateRole(userId: String, tradeUserId: String) {
        // 자신의 글만 수정 및 삭제할 수 있다.
        if (tradeUserId != userId) {
            throw ItdaException(ErrorCode.OTHERS_TRADE)
        }
    }

    /**
     * 이미지 파일 ID 검증
     */
    fun validateImageFileId(fileId: String) {
        if (!commonImageMapper.selectFileIdChk(fileId = fileId)) {
            throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        }
    }

    /**
     * 거래 희망 방식 (HopeMethod) 검증
     */
    fun validateHopeMethod(hopeMethod: String) {
        runCatching { TradeHopeMethod.valueOf(hopeMethod) }
            .onFailure { throw ItdaException(ErrorCode.FORBIDDEN) }
    }

    /**
     * 직거래 정보 검증
     */
    fun validateDirectTradeInfo(
        hopeLocationLatitude: String?,
        hopeLocationLongitude: String?,
        hopeAddress: String?
    ) {
        if (hopeLocationLatitude.isNullOrBlank() || hopeLocationLongitude.isNullOrBlank() || hopeAddress.isNullOrBlank()) {
            throw ItdaException(ErrorCode.INVALID_TRADE_ADDRESS_INFO)
        }
    }

    /**
     * 교환 상품 검증
     */
    fun validateExchangeItem(giveItemId: Long, wantItemId: Long) {
        if (giveItemId == wantItemId) {
            throw ItdaException(ErrorCode.SAME_EXCHANGE_ITEMS)
        }
    }

    /**
     * 판매 가격 검증
     */
    fun validateSalesPrice(price: Int) {
        if (price < 1 || price % 10 != 0) {
            throw ItdaException(ErrorCode.INVALID_SALES_PRICE)
        }
    }

    /**
     * Trade 상태값 검증 (삭제 시)
     */
    fun validateTradeStatus(trade: Trade) {
        if (trade.status == TradeStatus.DELETED) {
            throw ItdaException(ErrorCode.FORBIDDEN)
        }
        if (trade.status == TradeStatus.ENDED) {
            throw ItdaException(ErrorCode.CANNOT_DELETE_ENDED_TRADE)
        }
    }

    /**
     * TradeItem 상태값 검증 (삭제 시)
     */
    fun validateTradeItemStatus(tradeId: String) {
        if (commonTradeMapper.selectTradeItemStatusCompletedChk(tradeId = tradeId)) {
            throw ItdaException(ErrorCode.CANNOT_DELETE_COMPLETED_TRADE)
        }
    }

    /**
     * TradeSuggest 상태값 검증
     */
    fun validateSuggestStatus(tradeItemId: String) {
        // 해당 TradeItem 하위에 PENDING, APPROVED, COMPLETED인 제안이 하나라도 존재하면 수정 불가능
        val statusCheckMap = commonTradeMapper.selectTradeSuggestStatusChk(tradeItemId = tradeItemId)
        val pendingYn = statusCheckMap["pending_yn"]
        val approvedYn = statusCheckMap["approved_yn"]
        val completedYn = statusCheckMap["completed_yn"]

        if (pendingYn != null && approvedYn != null && completedYn != null) {
            if (pendingYn == "Y") {
                throw ItdaException(ErrorCode.PENDING_SUGGEST_EXISTS)
            }
            if (approvedYn == "Y") {
                throw ItdaException(ErrorCode.APPROVED_SUGGEST_EXISTS)
            }
            if (completedYn == "Y") {
                throw ItdaException(ErrorCode.COMPLETED_SUGGEST_EXISTS)
            }
        } else {
            throw ItdaException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * TradeSuggest 상태값 검증 (삭제 시)
     */
    fun validateSuggestStatusByTradeId(tradeId: String) {
        val statusMap = commonTradeMapper.selectTradeSuggestStatusChkByTradeId(tradeId = tradeId)

        val pendingChk = statusMap["pending_chk"]
        val approvedChk = statusMap["approved_chk"]
        val completedChk = statusMap["completed_chk"]

        if (pendingChk != null && approvedChk != null && completedChk != null) {
            if (pendingChk > 0) {
                throw ItdaException(ErrorCode.PENDING_SUGGEST_EXISTS)
            }
            if (approvedChk > 0) {
                throw ItdaException(ErrorCode.APPROVED_SUGGEST_EXISTS)
            }
            if (completedChk > 0) {
                throw ItdaException(ErrorCode.COMPLETED_SUGGEST_EXISTS)
            }
        }
    }
}