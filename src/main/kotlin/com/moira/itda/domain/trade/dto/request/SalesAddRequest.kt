package com.moira.itda.domain.trade.dto.request

data class SalesAddRequest(
    override val title: String,
    override val content: String,
    override val fileId: String,
    override val hopeMethod: String,
    override val hopeLocation: String?,
    override val hopeAddress: String?,
    override val hopeLocationLatitude: String?,
    override val hopeLocationLongitude: String?,
    val items: List<SalesItemAddRequest>
) : TradeRequest