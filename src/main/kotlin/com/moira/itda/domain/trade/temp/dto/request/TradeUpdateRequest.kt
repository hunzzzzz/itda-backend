package com.moira.itda.domain.trade.temp.dto.request

import com.moira.itda.domain.trade.add.dto.request.TradeCommonRequest

// 판매, 교환 등록 시 필요한 공통 필드
interface TradeUpdateRequest : TradeCommonRequest {
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