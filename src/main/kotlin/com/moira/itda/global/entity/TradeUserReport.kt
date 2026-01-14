package com.moira.itda.global.entity

import com.moira.itda.domain.trade_user_report.dto.request.ReportRequest
import java.time.ZonedDateTime

data class TradeUserReport(
    val id: Long?,
    val chatRoomId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val reportUserId: String,
    val reportedUserId: String,
    val reportReason: String,
    val reportAt: ZonedDateTime
) {
    companion object {
        fun from(userId: String, chatRoomId: String, request: ReportRequest): TradeUserReport {
            return TradeUserReport(
                id = null,
                chatRoomId = chatRoomId,
                tradeId = request.tradeId,
                tradeSuggestId = request.tradeSuggestId,
                gachaId = request.gachaId,
                reportUserId = userId,
                reportedUserId = request.userId,
                reportReason = request.reportReason,
                reportAt = ZonedDateTime.now()
            )
        }
    }
}