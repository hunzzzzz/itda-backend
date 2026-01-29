package com.moira.itda.domain.trade.update.service

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.trade.update.component.TradeValidator
import com.moira.itda.domain.trade.update.dto.request.ExchangeUpdateRequest
import com.moira.itda.domain.trade.update.dto.request.SalesUpdateRequest
import com.moira.itda.domain.trade.update.dto.request.TradeUpdateCommonRequest
import com.moira.itda.domain.trade.update.mapper.TradeUpdateMapper
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.entity.TradeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeUpdateService(
    private val commonImageMapper: CommonImageMapper,
    private val mapper: TradeUpdateMapper,
    private val s3Handler: AwsS3Handler,
    private val validator: TradeValidator
) {
    /**
     * 거래수정 > 유효성 검사 (공통)
     */
    private fun validateCommon(userId: String, trade: Trade, request: TradeUpdateCommonRequest) {
        // 권한 검증
        validator.validateRole(userId = userId, tradeUserId = trade.userId)

        // 삭제하려는 교환쌍(TradeItem) 하위 제안(TradeSuggest)들의 상태값 검증
        if (!request.deleteItems.isNullOrEmpty()) {
            request.deleteItems?.forEach { validator.validateSuggestStatus(tradeItemId = it) }
        }

        if (request.imageChangeYn == "Y") {
            validator.validateImageFileId(fileId = request.fileId)
        }

        validator.validateHopeMethod(hopeMethod = request.hopeMethod)
    }

    /**
     * 교환수정 > 유효성 검사
     */
    private fun validateExchange(userId: String, trade: Trade, request: ExchangeUpdateRequest) {
        // 공통 유효성 검사
        this.validateCommon(userId = userId, trade = trade, request = request)

        // 새로 추가되거나 수정된 교환쌍을 검증 (giveItemId != wantItemId)
        if (!request.updateItems.isNullOrEmpty()) {
            request.updateItems.forEach { item ->
                validator.validateExchangeItem(giveItemId = item.giveItemId, wantItemId = item.wantItemId)
            }
        }
        if (!request.newItems.isNullOrEmpty()) {
            request.newItems.forEach { item ->
                validator.validateExchangeItem(giveItemId = item.giveItemId, wantItemId = item.wantItemId)
            }
        }
    }

    /**
     * 판매수정 > 유효성 검사
     */
    private fun validateSales(userId: String, trade: Trade, request: SalesUpdateRequest) {
        // 공통 유효성 검사
        this.validateCommon(userId = userId, trade = trade, request = request)

        // 새로 추가되거나 수정된 판매 아이템의 가격을 검증
        if (!request.updateItems.isNullOrEmpty()) {
            request.updateItems.forEach { item ->
                validator.validateSalesPrice(price = item.price)
            }
        }
        if (!request.newItems.isNullOrEmpty()) {
            request.newItems.forEach { item ->
                validator.validateSalesPrice(price = item.price)
            }
        }
    }

    /**
     * 거래삭제 > 유효성 검사
     */
    private fun validateDelete(userId: String, trade: Trade, tradeItemId: String) {
        // 권한 검증
        validator.validateRole(userId = userId, tradeUserId = trade.userId)

        // 하위 제안들의 상태값 검증
        validator.validateSuggestStatus(tradeItemId = tradeItemId)
    }

    /**
     * 거래수정 > 공통
     */
    private fun update(trade: Trade, request: TradeUpdateCommonRequest) {
        // [3] 사용자가 이미지를 변경한 경우, 기존 이미지 파일 삭제 (AWS S3, DB)
        if (request.imageChangeYn == "Y") {
            val oldFileId = trade.fileId
            commonImageMapper.selectImageFileUrl(fileId = oldFileId).forEach { s3Handler.delete(it.fileUrl) }
            commonImageMapper.deleteImageFile(fileId = oldFileId)
        }

        // [4] Trade 수정
        mapper.updateTrade(tradeId = trade.id, request = request)

        if (trade.type == TradeType.EXCHANGE) {
            request as ExchangeUpdateRequest

            // [5] TradeItem 삭제
            if (!request.deleteItems.isNullOrEmpty()) {
                request.deleteItems?.forEach { tradeItemId ->
                    mapper.updateTradeItemStatusDeleted(tradeItemId = tradeItemId)
                }
            }

            // [6] TradeItem 수정
            if (!request.updateItems.isNullOrEmpty()) {
                request.updateItems?.forEach { item ->
                    mapper.updateTradeItemExchange(request = item)
                }
            }

            // [7] TradeItem 추가
            if (!request.newItems.isNullOrEmpty()) {
                request.newItems?.forEach { item ->
                    val tradeItem = TradeItem.from(tradeId = trade.id, gachaId = trade.gachaId, request = item)
                    mapper.insertTradeItem(tradeItem = tradeItem)
                }
            }
        } else if (trade.type == TradeType.SALES) {
            request as SalesUpdateRequest

            // [5] TradeItem 삭제
            if (!request.deleteItems.isNullOrEmpty()) {
                request.deleteItems?.forEach { tradeItemId ->
                    mapper.updateTradeItemStatusDeleted(tradeItemId = tradeItemId)
                }
            }

            // [6] TradeItem 수정
            if (!request.updateItems.isNullOrEmpty()) {
                request.updateItems.forEach { item ->
                    mapper.updateTradeItemSales(request = item)
                }
            }

            // [7] TradeItem 추가
            if (!request.newItems.isNullOrEmpty()) {
                request.newItems.forEach { item ->
                    val tradeItem = TradeItem.from(tradeId = trade.id, gachaId = trade.gachaId, request = item)
                    mapper.insertTradeItem(tradeItem = tradeItem)
                }
            }
        }
    }

    /**
     * 교환수정
     */
    @Transactional
    fun updateExchange(
        userId: String,
        tradeId: String,
        request: ExchangeUpdateRequest
    ) {
        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId) ?: throw ItdaException(ErrorCode.FORBIDDEN)

        // [2] 유효성 검사
        this.validateExchange(userId = userId, trade = trade, request = request)

        // [3] 수정 (공통)
        this.update(trade = trade, request = request)
    }

    /**
     * 판매수정
     */
    @Transactional
    fun updateSales(
        userId: String,
        tradeId: String,
        request: SalesUpdateRequest
    ) {
        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId) ?: throw ItdaException(ErrorCode.FORBIDDEN)

        // [2] 유효성 검사
        this.validateSales(userId = userId, trade = trade, request = request)

        // [3] 수정 (공통)
        this.update(trade = trade, request = request)
    }

    /**
     * 거래삭제
     */
    @Transactional
    fun delete(userId: String, tradeId: String, tradeItemId: String) {
        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId) ?: throw ItdaException(ErrorCode.FORBIDDEN)

        // [2] 유효성 검사
        this.validateDelete(userId = userId, trade = trade, tradeItemId = tradeItemId)

        // [3] TODO: TradeSuggest 삭제 처리

        // [4] TradeItem 삭제 처리 (status: DELETED)
        mapper.updateTradeItemStatusDeleted(tradeItemId = tradeItemId)

        // [5] 모든 TradeItem이 DELETED이면 Trade 삭제 처리
        if (!mapper.selectTradeItemStatusNotDeletedChk(tradeId = tradeId)) {
            // [5-1] 이미지 파일 삭제 (AWS S3)
            commonImageMapper.selectImageFileUrl(fileId = trade.fileId)
                .forEach { s3Handler.delete(fileUrl = it.fileUrl) }

            // [5-2] Trade 삭제 처리 (status: DELETED)
            mapper.updateTradeStatusDeleted(tradeId = tradeId)
        }
    }
}