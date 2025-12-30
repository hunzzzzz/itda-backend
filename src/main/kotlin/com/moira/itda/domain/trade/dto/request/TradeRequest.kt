package com.moira.itda.domain.trade.dto.request

interface TradeRequest {
    val title: String
    val content: String
    val fileId: String
    val hopeMethod: String
    val hopeLocation: String?
    val hopeAddress: String?
    val hopeLocationLatitude: String?
    val hopeLocationLongitude: String?
}