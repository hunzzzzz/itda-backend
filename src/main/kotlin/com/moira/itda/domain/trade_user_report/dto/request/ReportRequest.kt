package com.moira.itda.domain.trade_user_report.dto.request

data class ReportRequest(
    val userId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val reportReason: String
)