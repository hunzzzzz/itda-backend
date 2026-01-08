package com.moira.itda.domain.gacha.dto.response

data class GachaDetailResponse(
    val gacha: GachaResponse,
    val items: List<GachaItemResponse>,
    val myWishYn: String,
    val myPickedItems: List<MyPickedItemResponse>,
    val myPlaces: List<MyPlaceResponse>
)