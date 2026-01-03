package com.moira.itda.domain.trade.dto.request

// 판매, 교환 등록 시 필요한 공통 필드
interface TradeUpdateRequest : TradeRequest {
    override val title: String
    override val content: String?
    override val fileId: String
    override val hopeMethod: String
    override val hopeLocation: String?
    override val hopeAddress: String?
    override val hopeLocationLatitude: String?
    override val hopeLocationLongitude: String?
    val imageChangeYn: String
}