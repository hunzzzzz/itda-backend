package com.moira.itda.domain.trade_user_report.mapper

import com.moira.itda.global.entity.TradeUserReport
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeUserReportMapper {
    /**
     * 유저신고 > TradeUserReport 저장
     */
    fun insertTradeUserReport(tradeUserReport: TradeUserReport)

    /**
     * 유저신고 > TradeSuggest status 조회
     */
    fun selectTradeSuggestStatus(tradeSuggestId: String): String?
}