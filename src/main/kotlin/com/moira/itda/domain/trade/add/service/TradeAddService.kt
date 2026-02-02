package com.moira.itda.domain.trade.add.service

import com.moira.itda.domain.trade.add.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.add.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.add.dto.request.TradeCommonRequest
import com.moira.itda.domain.trade.add.dto.response.GachaIdResponse
import com.moira.itda.domain.trade.add.mapper.TradeAddMapper
import com.moira.itda.domain.trade.common.component.TradeValidator
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeHopeMethod
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.entity.TradeType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeAddService(
    private val mapper: TradeAddMapper,
    private val validator: TradeValidator
) {
    /**
     * 공통 > 유효성 검사
     */
    private fun validateCommon(request: TradeCommonRequest) {
        // 이미지 파일 업로드 여부 확인
        validator.validateImageFileId(fileId = request.fileId)

        // 거래 희망 방식 검증
        validator.validateHopeMethod(hopeMethod = request.hopeMethod)

        // 직거래인 경우, 반드시 위도, 경도, 주소 정보가 있어야 한다.
        if (request.hopeMethod == TradeHopeMethod.DIRECT.name) {
            validator.validateDirectTradeInfo(
                hopeLocationLatitude = request.hopeLocationLatitude,
                hopeLocationLongitude = request.hopeLocationLongitude,
                hopeAddress = request.hopeAddress
            )
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
            validator.validateExchangeItem(giveItemId = item.giveItemId, wantItemId = item.wantItemId)
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
            validator.validateSalesPrice(price = item.price)
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