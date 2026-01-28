package com.moira.itda.domain.trade.add.service

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.trade.add.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.add.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.add.dto.request.TradeCommonRequest
import com.moira.itda.domain.trade.add.mapper.TradeAddMapper
import com.moira.itda.domain.trade.temp.dto.response.GachaIdResponse
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.entity.TradeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeAddService(
    private val commonImageMapper: CommonImageMapper,
    private val mapper: TradeAddMapper
) {
    private fun validateCommon(request: TradeCommonRequest) {
        // 이미지 파일 업로드 여부 확인
        if (!commonImageMapper.selectFileIdChk(fileId = request.fileId)) {
            throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        }

        // 거래 희망 방식 검증
        runCatching { TradeHopeMethod.valueOf(request.hopeMethod) }
            .onFailure { throw ItdaException(ErrorCode.FORBIDDEN) }

        // 직거래인 경우, 반드시 위도, 경도, 주소 정보가 있어야 한다.
        if (request.hopeMethod == TradeHopeMethod.DIRECT.name) {
            if (request.hopeLocationLatitude.isNullOrBlank() || request.hopeLocationLongitude.isNullOrBlank() || request.hopeAddress.isNullOrBlank()) {
                throw ItdaException(ErrorCode.INVALID_TRADE_ADDRESS_INFO)
            }
        }
    }

    /**
     * 교환등록 > 유효성 검사
     */
    private fun validateExchange(request: ExchangeAddRequest) {
        // [1] 공통 유효성 검사
        this.validateCommon(request = request)

        // [2] 같은 아이템끼리 교환할 수 없다.
        request.items.forEach { item ->
            if (item.giveItemId == item.wantItemId) {
                throw ItdaException(ErrorCode.SAME_EXCHANGE_ITEMS)
            }
        }
    }

    /**
     * 판매등록 > 유효성 검사
     */
    private fun validateSales(request: SalesAddRequest) {
        // [1] 사용자 입력값에 대한 공통 유효성 검사
        this.validateCommon(request = request)

        // [2] 판매 가격 검사
        request.items.forEach { item ->
            if (item.price < 1 || item.price % 10 != 0) {
                throw ItdaException(ErrorCode.INVALID_SALES_PRICE)
            }
        }
    }

    /**
     * 교환등록
     */
    @Transactional
    fun exchange(
        userId: String,
        gachaId: String,
        request: ExchangeAddRequest
    ): GachaIdResponse {
        // [1] 유효성 검사
        this.validateExchange(request = request)

        // [2] Trade 저장
        val trade = Trade.from(type = TradeType.EXCHANGE, userId = userId, gachaId = gachaId, request = request)
        mapper.insertTrade(trade = trade)

        // [3] TradeItem 저장
        request.items.forEach {
            val tradeItem = TradeItem.from(tradeId = trade.id, gachaId = gachaId, request = it)
            mapper.insertTradeItem(tradeItem = tradeItem)
        }

        // [4] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }

    /**
     * 판매등록
     */
    @Transactional
    fun sales(
        userId: String,
        gachaId: String,
        request: SalesAddRequest
    ): GachaIdResponse {
        // [1] 유효성 검사
        this.validateSales(request = request)

        // [2] Trade 저장
        val trade = Trade.from(type = TradeType.SALES, userId = userId, gachaId = gachaId, request = request)
        mapper.insertTrade(trade = trade)

        // [3] TradeItem 저장
        request.items.forEach {
            val tradeItem = TradeItem.from(tradeId = trade.id, gachaId = gachaId, request = it)
            mapper.insertTradeItem(tradeItem = tradeItem)
        }

        // [4] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }
}