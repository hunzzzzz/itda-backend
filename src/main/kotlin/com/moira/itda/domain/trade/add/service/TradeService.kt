package com.moira.itda.domain.trade.add.service

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.trade.add.dto.request.*
import com.moira.itda.domain.trade.add.dto.response.*
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import com.moira.itda.global.entity.TradeItemType
import com.moira.itda.global.entity.TradeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeService(
    private val commonImageMapper: CommonImageMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val mapper: com.moira.itda.domain.trade.add.mapper.TradeMapper,
    private val s3Handler: AwsS3Handler,
    private val validator: com.moira.itda.domain.trade.add.component.TradeValidator
) {
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
        validator.validateExchangeAdd(request = request)

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
    fun sale(
        userId: String,
        gachaId: String,
        request: SalesAddRequest
    ): GachaIdResponse {
        // [1] 유효성 검사
        validator.validateSalesAdd(request = request)

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

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > 거래 아이템 목록 조회
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > 거래 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getTradeItemList(tradeId: String): List<TradeItemResponse> {
        // [1] TradeItem 목록 조회
        return mapper.selectTradeItemList(tradeId = tradeId)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > 거래 정보 조회
     */
    @Transactional(readOnly = true)
    fun getTrade(tradeId: String): TradeDetailContentResponse {
        // [1] Trade 조회
        val trade = mapper.selectTradeDetail(tradeId = tradeId)

        // [2] 이미지 파일 URL 리스트 조회
        trade.fileUrlList = commonImageMapper.selectImageFileUrl(fileId = trade.fileId).map { it.fileUrl }

        // [3] TradeItem 리스트 조회
        val itemList = mapper.selectTradeItemList(tradeId = tradeId)

        // [4] DTO 병합 후 리턴
        return TradeDetailContentResponse(
            trade = trade,
            itemList = itemList
        )
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 교환수정 (공통)
     * 가챠정보 > 가챠목록 > 상세정보 > 판매수정 (공통)
     */
    private fun update(trade: Trade, request: TradeUpdateRequest) {
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
            if (request.deleteItems != null && request.deleteItems.isNotEmpty()) {
                request.deleteItems.forEach { tradeItemId ->
                    mapper.updateTradeItemStatusDeleted(tradeItemId = tradeItemId)
                }
            }

            // [6] TradeItem 수정
            if (request.updateItems != null && request.updateItems.isNotEmpty()) {
                request.updateItems.forEach { item ->
                    mapper.updateTradeItemExchange(request = item)
                }
            }

            // [7] TradeItem 추가
            if (request.newItems != null && request.newItems.isNotEmpty()) {
                request.newItems.forEach { item ->
                    val tradeItem = TradeItem.from(tradeId = trade.id, gachaId = trade.gachaId, request = item)
                    mapper.insertTradeItem(tradeItem = tradeItem)
                }
            }
        } else if (trade.type == TradeType.SALES) {
            request as SalesUpdateRequest

            // [5] TradeItem 삭제
            if (request.deleteItems != null && request.deleteItems.isNotEmpty()) {
                request.deleteItems.forEach { tradeItemId ->
                    mapper.updateTradeItemStatusDeleted(tradeItemId = tradeItemId)
                }
            }

            // [6] TradeItem 수정
            if (request.updateItems != null && request.updateItems.isNotEmpty()) {
                request.updateItems.forEach { item ->
                    mapper.updateTradeItemSales(request = item)
                }
            }

            // [7] TradeItem 추가
            if (request.newItems != null && request.newItems.isNotEmpty()) {
                request.newItems.forEach { item ->
                    val tradeItem = TradeItem.from(tradeId = trade.id, gachaId = trade.gachaId, request = item)
                    mapper.insertTradeItem(tradeItem = tradeItem)
                }
            }
        }
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 교환수정
     */
    @Transactional
    fun updateExchange(
        userId: String,
        tradeId: String,
        request: ExchangeUpdateRequest
    ) {
        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId)
            ?: throw ItdaException(ErrorCode.TRADE_NOT_FOUND)

        // [2] 유효성 검사
        validator.validateUpdate(userId = userId, tradeUserId = trade.userId, request = request)

        // [3] 수정 (공통)
        this.update(trade = trade, request = request)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 판매수정
     */
    @Transactional
    fun updateSales(
        userId: String,
        tradeId: String,
        request: SalesUpdateRequest
    ) {
        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId) ?: throw ItdaException(ErrorCode.TRADE_NOT_FOUND)

        // [2] 유효성 검사
        validator.validateUpdate(userId = userId, tradeUserId = trade.userId, request = request)

        // [3] 수정 (공통)
        this.update(trade = trade, request = request)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제
     */
    @Transactional
    fun delete(userId: String, tradeId: String, tradeItemId: String) {
        // [1] Trade 조회
        val trade = mapper.selectTrade(tradeId = tradeId)
            ?: throw ItdaException(ErrorCode.TRADE_NOT_FOUND)

        // [2] 유효성 검사
        validator.validateDelete(userId = userId, tradeUserId = trade.userId, tradeItemId = tradeItemId)

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

    /**
     * 내 활동 > 내 거래 목록 조회
     */
    @Transactional(readOnly = true)
    fun getMyTradeList(
        userId: String,
        page: Int,
        type: String
    ): TradePageResponse {
        // [1] 유효성 검사
        kotlin.runCatching { TradeItemType.valueOf(type) }
            .onFailure { throw ItdaException(ErrorCode.INVALID_TRADE_TYPE) }

        // [2] 변수 세팅
        val pageSize = MY_TRADE_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [3] 거래 목록 조회
        val totalElements = mapper.selectMyTradeListCnt(userId = userId, type = type)
        val tradeList = mapper.selectMyTradeList(
            userId = userId,
            type = type,
            pageSize = pageSize,
            offset = offset
        )

        // [4] 하위 교환/판매 목록 조회
        val contents = tradeList.map { trade ->
            TradeContentResponse(
                trade = trade,
                items = mapper.selectTradeItemList(tradeId = trade.tradeId)
            )
        }

        // [5] 오프셋 기반 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [6] DTO 병합 후 리턴
        return TradePageResponse(
            content = contents,
            page = pageResponse
        )
    }
}