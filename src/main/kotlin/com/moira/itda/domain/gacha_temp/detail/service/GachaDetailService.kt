package com.moira.itda.domain.gacha_temp.detail.service

import com.moira.itda.domain.gacha_temp.detail.dto.response.TradeContentResponse
import com.moira.itda.domain.gacha_temp.detail.dto.response.TradePageResponse
import com.moira.itda.domain.gacha_temp.detail.mapper.GachaDetailMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_DETAIL_TRADE_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaDetailService(
    private val gachaDetailMapper: GachaDetailMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
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

}