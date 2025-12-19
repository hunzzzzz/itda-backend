package com.moira.itda.domain.user.mytrade.service

import com.moira.itda.domain.user.mytrade.dto.MyTradePageResponse
import com.moira.itda.domain.user.mytrade.dto.MyTradeContentResponse
import com.moira.itda.domain.user.mytrade.mapper.MyTradeMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyTradeService(
    private val myTradeMapper: MyTradeMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    /**
     * 마이페이지 > 내 거래 목록 조회
     */
    @Transactional(readOnly = true)
    fun getTrades(userId: String, page: Int, type: String): MyTradePageResponse {
        // [1] 유효성 검사
        if (type != "SALES" && type != "EXCHANGE") {
            throw ItdaException(ErrorCode.INVALID_TRADE_TYPE)
        }

        // [2] 변수 세팅
        val pageSize = MY_TRADE_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 거래 목록 조회
        val totalElements = myTradeMapper.selectTradeListCnt(userId = userId, type = type)
        val tradeList = myTradeMapper.selectTradeList(
            userId = userId,
            type = type,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 거래 아이템 목록 조회
        val contents = when (type) {
            "SALES" -> {
                tradeList.map { trade ->
                    val salesItemList = myTradeMapper.selectSalesItemList(tradeId = trade.tradeId)

                    MyTradeContentResponse(trade = trade, items = salesItemList)
                }
            }
            "EXCHANGE" -> {
                tradeList.map { trade ->
                    val exchangeItemList = myTradeMapper.selectExchangeItemList(tradeId = trade.tradeId)

                    MyTradeContentResponse(trade = trade, items = exchangeItemList)
                }
            }
            else -> {
                emptyList()
            }
        }

        // [4] 오프셋 기반 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page,
            pageSize = pageSize,
            totalElements = totalElements
        )

        // [5] DTO 병합 후 리턴
        return MyTradePageResponse(content = contents, page = pageResponse)
    }
}