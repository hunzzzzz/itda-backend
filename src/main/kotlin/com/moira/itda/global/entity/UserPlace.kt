package com.moira.itda.global.entity

import com.moira.itda.domain.user_place.dto.request.UserPlaceRequest
import java.time.ZonedDateTime
import java.util.*

data class UserPlace(
    val id: String,
    val userId: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val name: String,
    val isDefault: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun from(userId: String, request: UserPlaceRequest, isDefault: String): UserPlace {
            return UserPlace(
                id = UUID.randomUUID().toString(),
                userId = userId,
                latitude = request.latitude,
                longitude = request.longitude,
                address = request.address,
                name = request.name,
                isDefault = isDefault,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}
