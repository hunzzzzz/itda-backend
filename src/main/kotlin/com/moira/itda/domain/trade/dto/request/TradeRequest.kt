package com.moira.itda.domain.trade.dto.request

// 판매, 교환 등록 시 필요한 공통 필드
interface TradeRequest {
    val title: String
    val content: String?
    val fileId: String
    val hopeMethod: String
    val hopeLocation: String?
    val hopeAddress: String?
    val hopeLocationLatitude: String?
    val hopeLocationLongitude: String?
}