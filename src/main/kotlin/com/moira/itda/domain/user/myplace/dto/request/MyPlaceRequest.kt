package com.moira.itda.domain.user.myplace.dto.request

data class MyPlaceRequest(
    val latitude: String,
    val longitude: String,
    val address: String,
    val name: String
)
