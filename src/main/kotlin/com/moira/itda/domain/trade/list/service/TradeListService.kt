package com.moira.itda.domain.trade.list.service

import com.moira.itda.domain.trade.list.dto.response.TradeListContentResponse
import com.moira.itda.domain.trade.list.dto.response.TradeListPageResponse
import com.moira.itda.domain.trade.list.mapper.TradeListMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_TRADE_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeListService(
    private val mapper: TradeListMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 거래목록 조회
     */
    @Transactional(readOnly = true)
    fun getTradeList(
        gachaId: String,
        page: Int,
        placeId: String?,
        onlyPending: String,
        gachaItemId: Long?
    ): TradeListPageResponse {
        // [1] 변수 세팅
        val pageSize = GACHA_TRADE_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] Trade 목록 조회
        val totalElements = mapper.selectTradeListCnt(
            gachaId = gachaId, placeId = placeId, onlyPending = onlyPending, gachaItemId = gachaItemId
        )
        val trades = mapper.selectTradeList(
            gachaId = gachaId,
            placeId = placeId,
            onlyPending = onlyPending,
            gachaItemId = gachaItemId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 하위 교환/판매 목록 조회
        val contents = trades.map { trade ->
            TradeListContentResponse(
                trade = trade,
                items = mapper.selectTradeItemList(tradeId = trade.tradeId)
            )
        }

        // [4] 오프셋 기반 페이지네이션 구현
        val pageResponse = pageHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [5] DTO 병합 후 리턴
        return TradeListPageResponse(content = contents, page = pageResponse)
    }
}