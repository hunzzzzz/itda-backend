package com.moira.itda.domain.user.mytrade.mapper

import com.moira.itda.domain.user.mytrade.dto.MyExchangeItemResponse
import com.moira.itda.domain.user.mytrade.dto.MySalesItemResponse
import com.moira.itda.domain.user.mytrade.dto.MyTradeResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyTradeMapper {
    /**
     * 마이페이지 > 내 거래 목록 조회 > totalElements 계산
     */
    fun selectTradeListCnt(userId: String, type: String): Long

    /**
     * 마이페이지 > 내 거래 목록 조회
     */
    fun selectTradeList(userId: String, type: String, pageSize: Int, offset: Int): List<MyTradeResponse>

    /**
     * 마이페이지 > 내 거래 목록 조회 > 판매 아이템 목록 조회
     */
    fun selectSalesItemList(tradeId: String): List<MySalesItemResponse>

    /**
     * 마이페이지 > 내 거래 목록 조회 > 교환 아이템 목록 조회
     */
    fun selectExchangeItemList(tradeId: String): List<MyExchangeItemResponse>
}