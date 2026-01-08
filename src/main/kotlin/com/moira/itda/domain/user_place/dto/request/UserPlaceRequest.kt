package com.moira.itda.domain.user_place.dto.request

data class UserPlaceRequest(
    val latitude: String,
    val longitude: String,
    val address: String,
    val name: String
)
