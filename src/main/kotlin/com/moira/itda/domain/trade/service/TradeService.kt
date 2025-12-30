package com.moira.itda.domain.trade.service

import com.moira.itda.domain.common.mapper.CommonMapper
import com.moira.itda.domain.trade.component.TradeValidator
import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.dto.request.ExchangeUpdateRequest
import com.moira.itda.domain.trade.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.dto.response.GachaIdResponse
import com.moira.itda.domain.trade.dto.response.TradeContentResponse
import com.moira.itda.domain.trade.dto.response.TradePageResponse
import com.moira.itda.domain.trade.mapper.TradeMapper
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.entity.TradeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_DETAIL_TRADE_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeService(
    private val awsS3Handler: AwsS3Handler,
    private val commonMapper: CommonMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val mapper: TradeMapper,
    private val validator: TradeValidator
) {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 교환 > 진행 중인 교환글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkTradeExchange(userId: String, gachaId: String) {
        // [1] 유효성 검사
        validator.validateExchangeExists(userId = userId, gachaId = gachaId)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 판매 > 진행 중인 판매글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkTradeSales(userId: String, gachaId: String) {
        // [1] 유효성 검사
        validator.validateSalesExists(userId = userId, gachaId = gachaId)
    }

    /**
     * 교환등록
     */
    @Transactional
    fun exchange(userId: String, gachaId: String, request: ExchangeAddRequest): GachaIdResponse {
        // [1] 유효성 검사
        validator.validateExchange(userId = userId, gachaId = gachaId, request = request)

        // [2] Trade 저장
        val trade = Trade.fromRequest(type = TradeType.EXCHANGE, userId = userId, gachaId = gachaId, request = request)
        mapper.insertTrade(trade = trade)

        // [3] TradeItem 저장
        request.items.forEach { item ->
            val tradeExchangeItem = TradeItem.fromRequest(
                tradeId = trade.id, gachaId = gachaId, request = item
            )
            mapper.insertTradeItem(tradeItem = tradeExchangeItem)
        }

        // [4] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }

    /**
     * 판매등록
     */
    @Transactional
    fun sale(userId: String, gachaId: String, request: SalesAddRequest): GachaIdResponse {
        // [1] 유효성 검사
        validator.validateSales(userId = userId, gachaId = gachaId, request = request)

        // [2] Trade 저장
        val trade = Trade.fromRequest(type = TradeType.SALES, userId = userId, gachaId = gachaId, request = request)
        mapper.insertTrade(trade = trade)

        // [3] TradeItem 저장
        request.items.forEach { item ->
            val tradeSalesItem = TradeItem.fromRequest(
                gachaId = gachaId, tradeId = trade.id, request = item
            )
            mapper.insertTradeItem(tradeItem = tradeSalesItem)
        }

        // [4] gachaId 리턴
        return GachaIdResponse(gachaId = gachaId)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 목록 조회
     */
    @Transactional(readOnly = true)
    fun getTradeList(gachaId: String, page: Int, onlyPending: String, gachaItemId: Long?): TradePageResponse {
        // [1] 변수 세팅
        val pageSize = GACHA_DETAIL_TRADE_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 조회
        val totalElements = mapper.selectTradeListCnt(
            gachaId = gachaId, onlyPending = onlyPending, gachaItemId = gachaItemId
        )
        val trades = mapper.selectTradeList(
            gachaId = gachaId,
            onlyPending = onlyPending,
            gachaItemId = gachaItemId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 하위 교환/판매 목록 조회
        val contents = trades.map { trade ->
            TradeContentResponse(
                trade = trade,
                itemList = mapper.selectTradeItemList(tradeId = trade.tradeId, gachaId = gachaId)
            )
        }

        // [4] 오프셋 기반 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [5] DTO 병합 후 리턴
        return TradePageResponse(content = contents, page = pageResponse)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 교환 수정
     */
    @Transactional
    fun updateExchange(userId: String, gachaId: String, tradeId: String, request: ExchangeUpdateRequest) {
        println("imageChangeYn : ${request.imageChangeYn}")

        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId) ?: throw ItdaException(ErrorCode.TRADE_NOT_FOUND)

        println("기존 fileId: ${trade.fileId}")
        println("신규 fileId: ${request.fileId}")

        // [2] 유효성 검사
        validator.validateExchange(userId = userId, gachaId = gachaId, trade = trade, request = request)

        // [3] 사용자가 이미지를 변경한 경우, 기존 이미지 파일 삭제 (AWS S3, DB)
        if (request.imageChangeYn == "Y") {
            val oldFileId = trade.fileId
            commonMapper.selectImageFileUrl(fileId = oldFileId).forEach { awsS3Handler.delete(it.fileUrl) }
            commonMapper.deleteImageFile(fileId = oldFileId)
        }

        // [4] Trade 수정
        mapper.updateTrade(tradeId = tradeId, request = request)

        // [5] TradeItem 수정
        request.items.forEach { item -> mapper.updateTradeExchangeItem(request = item) }
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제
     */
    @Transactional
    fun deleteTrade(userId: String, tradeId: String) {
        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId) ?: throw ItdaException(ErrorCode.TRADE_NOT_FOUND)

        // [2] 유효성 검사
        validator.validateDeleteTrade(userId = userId, trade = trade)

        // [3] TradeSuggest, TradeItem 삭제
        mapper.deleteTradeSuggest(tradeId = tradeId)
        mapper.deleteTradeItem(tradeId = tradeId)

        // [4] 이미지 파일 삭제 (AWS S3)
        commonMapper.selectImageFileUrl(fileId = trade.fileId)
            .forEach { awsS3Handler.delete(fileUrl = it.fileUrl) }

        // [5] Trade 삭제
        mapper.deleteTrade(tradeId = tradeId)
    }
}