package com.moira.itda.domain.sales.service

import com.moira.itda.domain.sales.dto.request.SalesAddRequest
import com.moira.itda.domain.sales.mapper.SalesMapper
import com.moira.itda.domain.sales.dto.response.SalesItemResponse
import com.moira.itda.domain.sales.dto.response.GachaIdResponse
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class SalesService(
    private val salesMapper: SalesMapper
) {
    /**
     * 판매등록 > 하위 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaItems(gachaId: String): List<SalesItemResponse> {
        return salesMapper.selectGachaItemList(gachaId = gachaId)
    }

    /**
     * 판매등록 > 유효성 검사
     */
    private fun validate(userId: String, gachaId: String, request: SalesAddRequest) {
        // 필드값 검증
        if (request.title.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_TITLE)
        }
        if (request.content.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_CONTENT)
        }
        if (request.fileId.isBlank()) {
            throw ItdaException(ErrorCode.NO_TRADE_FILE_ID)
        }
        try {
            TradeHopeMethod.valueOf(request.hopeMethod)
        } catch (_: Exception) {
            throw ItdaException(ErrorCode.INVALID_TRADE_HOPE_METHOD)
        }
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_TRADE_ITEMS)
        }

        for (item in request.items) {
            if (item.count < 1) {
                throw ItdaException(ErrorCode.INVALID_TRADE_ITEM_COUNT)
            }
            if (item.price < 1) {
                throw ItdaException(ErrorCode.INVALID_TRADE_ITEM_PRICE)
            }
        }

        // 진행 중인 판매글이 있는지 검증
        if (salesMapper.selectSalesChk(userId = userId, gachaId = gachaId) > 0) {
            throw ItdaException(ErrorCode.PENDING_SALES_EXISTS)
        }

        // 이미지 파일 검증
        if (salesMapper.selectImageFileCnt(fileId = request.fileId) < 1) {
            throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        }
    }

    /**
     * 판매등록
     */
    @Transactional
    fun sale(userId: String, gachaId: String, request: SalesAddRequest): GachaIdResponse {
        // [1] 유효성 검사
        this.validate(userId = userId, gachaId = gachaId, request = request)

        // [2] 변수 세팅
        val tradeId = UUID.randomUUID().toString()

        // [3] Trade 저장
        val trade = Trade.fromSalesAddRequest(
            userId = userId,
            gachaId = gachaId,
            tradeId = tradeId,
            request = request
        )
        salesMapper.insertTrade(trade = trade)

        // [4] TradeItem 저장
        request.items.forEach { item ->
            val tradeSalesItem = TradeItem.fromSalesItemAddRequest(gachaId = gachaId, tradeId = tradeId, request = item)
            salesMapper.insertTradeItem(tradeItem = tradeSalesItem)
        }

        // [5] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }
}