package com.moira.itda.domain.user_temp.mytrade.mapper

import com.moira.itda.domain.user_temp.mytrade.dto.MyTradeItemResponse
import com.moira.itda.domain.user_temp.mytrade.dto.MyTradeResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyTradeMapper {

    /**
     * 마이페이지 > 내 거래 목록 조회 > 하위 교환/판매 아이템 목록 조회
     */
    fun selectTradeItemList(tradeId: String, type: String): List<MyTradeItemResponse>
}