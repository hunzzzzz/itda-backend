package com.moira.itda.domain.trade.delete.service

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.trade.common.component.TradeValidator
import com.moira.itda.domain.trade.common.mapper.TradeCommonMapper
import com.moira.itda.domain.trade.delete.mapper.TradeDeleteMapper
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeDeleteService(
    private val commonImageMapper: CommonImageMapper,
    private val commonTradeMapper: TradeCommonMapper,
    private val mapper: TradeDeleteMapper,
    private val s3Handler: AwsS3Handler,
    private val validator: TradeValidator
) {
    /**
     * 거래삭제 > 유효성 검사
     */
    private fun validateDelete(userId: String, trade: Trade) {
        // 권한 검증
        validator.validateRole(userId = userId, tradeUserId = trade.userId)

        // Trade 상태값 검증
        validator.validateTradeStatus(trade = trade)

        // TradeItem 상태값 검증
        validator.validateTradeItemStatus(tradeId = trade.id)

        // TradeSuggest 상태값 검증
        validator.validateSuggestStatusByTradeId(tradeId = trade.id)
    }

    /**
     * 거래삭제
     */
    @Transactional
    fun delete(userId: String, tradeId: String) {
        // [1] Trade 조회
        val trade = commonTradeMapper.selectTrade(tradeId = tradeId) ?: throw ItdaException(ErrorCode.FORBIDDEN)

        // [2] 유효성 검사
        this.validateDelete(userId = userId, trade = trade)

        // [3] TradeSuggest 삭제 처리 (status: DELETED)
        mapper.updateTradeSuggestStatusDeleted(tradeId = tradeId)

        // [4] TradeItem 삭제 처리 (status: DELETED)
        mapper.updateTradeItemStatusDeletedByTradeId(tradeId = tradeId)

        // [5] 이미지 파일 삭제 (AWS S3)
        commonImageMapper.selectImageFileUrl(fileId = trade.fileId)
            .forEach { s3Handler.delete(fileUrl = it.fileUrl) }
        commonImageMapper.deleteImageFile(fileId = trade.fileId)

        // [6] Trade 삭제 처리 (DELETED)
        mapper.updateTradeStatusDeleted(tradeId = tradeId)
    }
}