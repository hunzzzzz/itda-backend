package com.moira.itda.domain.trade.add.dto.request

/**
 * 사용자가 이미지를 변경한 경우,       imageChangeYn = Y, fileId = 새로운 파일 ID
 * 사용자가 이미지를 변경하지 않은 경우, imageChangeYn = N, fileId = 기존 파일 ID
 */
data class SalesUpdateRequest(
    override val title: String,
    override val content: String,
    override val fileId: String,
    override val hopeMethod: String,
    override val hopeLocation: String?,
    override val hopeAddress: String?,
    override val hopeLocationLatitude: String?,
    override val hopeLocationLongitude: String?,
    override val imageChangeYn: String,
    val deleteItems: List<String>?,
    val updateItems: List<com.moira.itda.domain.trade.add.dto.request.SalesItemUpdateRequest>?,
    val newItems: List<com.moira.itda.domain.trade.add.dto.request.SalesItemAddRequest>?
) : com.moira.itda.domain.trade.add.dto.request.TradeUpdateRequest