package com.moira.itda.global.entity

import com.moira.itda.domain.user.signup.dto.request.SignupRequest
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
    val fileId: String?,
    val refreshToken: String?,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromSignupRequest(request: SignupRequest, passwordEncoder: PasswordEncoder): User {
            return User(
                id = UUID.randomUUID().toString(),
                role = UserRole.USER,
                status = UserStatus.ACTIVE,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                nickname = request.nickname,
                fileId = null,
                refreshToken = null,
                lastLoginAt = null,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}