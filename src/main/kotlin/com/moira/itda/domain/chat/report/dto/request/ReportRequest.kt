package com.moira.itda.domain.chat.report.dto.request

data class ReportRequest(
    val userId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val reportReason: String
)