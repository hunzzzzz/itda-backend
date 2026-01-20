package com.moira.itda.global.entity

import com.moira.itda.domain.signup.dto.request.SignupRequest
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.ZonedDateTime
import java.util.*

data class User(
    val id: String,
    val role: UserRole,
    val status: UserStatus,
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val ci: String,
    val fileId: String?,
    val refreshToken: String?,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromRequest(
            request: SignupRequest,
            randomNickname: String,
            encoder: PasswordEncoder
        ): User {
            return User(
                id = UUID.randomUUID().toString(),
                role = UserRole.USER,
                status = UserStatus.ACTIVE,
                email = request.email,
                password = encoder.encode(request.password),
                name = request.name,
                nickname = randomNickname,
                phoneNumber = request.phoneNumber,
                ci = request.ci,
                fileId = null,
                refreshToken = null,
                lastLoginAt = null,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}