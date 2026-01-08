package com.moira.itda.domain.user_place.dto.response

import java.time.ZonedDateTime

data class UserPlaceResponse(
    val placeId: String,
    val userId: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val name: String,
    val isDefault: String,
    val createdAt: ZonedDateTime,
)
