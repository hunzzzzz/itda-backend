package com.moira.itda.domain.trade.common.service

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.trade.common.dto.response.TradeDetailContentResponse
import com.moira.itda.domain.trade.common.mapper.TradeCommonMapper
import com.moira.itda.domain.trade.list.mapper.TradeListMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeCommonService(
    private val commonImageMapper: CommonImageMapper,
    private val mapper: TradeCommonMapper,
    private val tradeListMapper: TradeListMapper
) {
    /**
     * 거래상세정보 조회
     */
    @Transactional(readOnly = true)
    fun getTrade(tradeId: String): TradeDetailContentResponse {
        // [1] Trade 조회
        val trade = mapper.selectTradeDetail(tradeId = tradeId)

        // [2] 이미지 파일 URL 리스트 조회
        trade.fileUrlList = commonImageMapper.selectImageFileUrl(fileId = trade.fileId)
            .map { it.fileUrl }

        // [3] TradeItem 리스트 조회
        val itemList = tradeListMapper.selectTradeItemList(tradeId = tradeId)

        // [4] DTO 병합 후 리턴
        return TradeDetailContentResponse(
            trade = trade,
            itemList = itemList
        )
    }
}