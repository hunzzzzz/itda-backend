package com.moira.itda.domain.gacha_temp.detail.service

import com.moira.itda.domain.gacha_temp.detail.dto.response.TradeContentResponse
import com.moira.itda.domain.gacha_temp.detail.dto.response.TradePageResponse
import com.moira.itda.domain.gacha_temp.detail.mapper.GachaDetailMapper
import com.moira.itda.global.entity.TradeStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_DETAIL_TRADE_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaDetailService(
    private val awsS3Handler: AwsS3Handler,
    private val gachaDetailMapper: GachaDetailMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 교환 > 진행 중인 교환글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkExchange(userId: String, gachaId: String) {
        if (gachaDetailMapper.selectExchangeChk(userId = userId, gachaId = gachaId) > 0) {
            throw ItdaException(ErrorCode.ALREADY_PENDING_EXCHANGE)
        }
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 판매 > 진행 중인 판매글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkSales(userId: String, gachaId: String) {
        if (gachaDetailMapper.selectSalesChk(userId = userId, gachaId = gachaId) > 0) {
            throw ItdaException(ErrorCode.ALREADY_PENDING_SALES)
        }
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회
     */
    @Transactional(readOnly = true)
    fun getTrades(gachaId: String, page: Int, onlyPending: String, gachaItemId: Long?): TradePageResponse {
        // [1] 변수 세팅
        val pageSize = GACHA_DETAIL_TRADE_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 조회
        val totalElements = gachaDetailMapper.selectTradeCnt(
            gachaId = gachaId, onlyPending = onlyPending, gachaItemId = gachaItemId
        )
        val trades = gachaDetailMapper.selectTradeList(
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
                itemList = gachaDetailMapper.selectTradeItemList(tradeId = trade.tradeId, gachaId = gachaId)
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

    private fun validate(userId: String, gachaId: String, tradeId: String) {
        // [1] Trade 조회 (by tradeId)
        val trade = gachaDetailMapper.selectTrade(gachaId = gachaId, tradeId = tradeId)
            ?: throw ItdaException(ErrorCode.TRADE_NOT_FOUND)

        // [2] 거래 status에 대한 유효성 검사 (COMPLETED된 거래가 있는지)
        if (trade.status == TradeStatus.COMPLETED) {
            throw ItdaException(ErrorCode.CANNOT_UPDATE_TRADE_WHEN_STATUS_IS_COMPLETED)
        }

        // [3] 삭제 권한에 대한 유효성 검사 (업로드한 유저 = 요청 유저인지)
        if (trade.userId != userId) {
            throw ItdaException(ErrorCode.CANNOT_UPDATE_TRADE_OF_OTHERS)
        }

        // [4] 제안 목록에 대한 유효성 검사 (APPROVED된 제안이 있는지)
        if (gachaDetailMapper.selectTradeSuggestChk(tradeId = tradeId) > 0) {
            throw ItdaException(ErrorCode.CANNOT_UPDATE_TRADE_WHEN_APPROVED_SUGGEST_EXISTS)
        }
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 거래 삭제
     */
    @Transactional
    fun deleteTrade(userId: String, gachaId: String, tradeId: String) {
        // [1] 유효성 검사
        this.validate(userId = userId, gachaId = gachaId, tradeId = tradeId)

        // [2] TradeSuggest, TradeItem 삭제
        gachaDetailMapper.deleteTradeSuggest(tradeId = tradeId)
        gachaDetailMapper.deleteTradeItem(tradeId = tradeId)

        // [3] 이미지 파일 삭제 (AWS S3)
        gachaDetailMapper.selectTradeFileUrlList(tradeId = tradeId)
            .forEach { awsS3Handler.delete(fileUrl = it) }

        // [4] Trade 삭제
        gachaDetailMapper.deleteTrade(tradeId = tradeId)
    }
}