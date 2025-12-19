package com.moira.itda.domain.exchange.mapper

import com.moira.itda.domain.exchange.dto.response.ExchangeItemResponse
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeExchangeItem
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ExchangeMapper {
    /**
     * 교환등록 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<ExchangeItemResponse>

    /**
     * 교환등록 > 진행 중인 교환글 존재 여부 확인
     */
    fun selectExchangeChk(gachaId: String, userId: String): Int

    /**
     * 교환등록 > 파일 ID 존재 여부 조회
     */
    fun selectImageFileCnt(fileId: String): Int

    /**
     * 교환등록 > Trade 저장
     */
    fun insertTrade(trade: Trade)

    /**
     * 교환등록 > TradeExchangeItem 저장
     */
    fun insertTradeExchangeItem(tradeExchangeItem: TradeExchangeItem)
}