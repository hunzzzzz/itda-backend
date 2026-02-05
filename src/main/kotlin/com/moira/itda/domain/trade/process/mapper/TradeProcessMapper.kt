package com.moira.itda.domain.trade.process.mapper

import com.moira.itda.domain.trade.process.dto.response.ProcessResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeProcessMapper {
    /**
     * 진행 중인 거래목록 조회 > totalElements 계산
     */
    fun selectTradeProcessResponseListCnt(userId: String): Long

    /**
     * 진행 중인 거래목록 조회
     */
    fun selectTradeProcessResponseList(userId: String, pageSize: Int, offset: Int): List<ProcessResponse>
}