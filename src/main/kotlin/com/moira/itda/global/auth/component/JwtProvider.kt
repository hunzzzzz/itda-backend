package com.moira.itda.global.auth.component

import com.moira.itda.global.auth.dto.UserAuth
import com.moira.itda.global.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.expiration-time.atk}")
    private val expirationTimeAtk: Long,
    @Value("\${jwt.expiration-time.rtk}")
    private val expirationTimeRtk: Long
) {
    @Value("\${jwt.secret.key}")
    private lateinit var secretKey: String

    private lateinit var key: SecretKey

    /**
     * SecretKey 정의
     */
    @PostConstruct
    fun init() {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))
    }

    /**
     * 토큰 생성
     */
    fun createToken(user: User, expirationTime: Long): String {
        val now = Date()
        val claims = HashMap<String, Any>().let {
            it["email"] = user.email
            it["nickname"] = user.nickname
            it["role"] = user.role.name
            it
        }

        return Jwts.builder()
            .subject(user.id)
            .claims(claims)
            .expiration(Date(now.time + expirationTime))
            .issuedAt(now)
            .issuer("ITDA")
            .signWith(key)
            .compact()
    }

    /**
     * AccessToken 생성
     */
    fun createAtk(user: User): String {
        return this.createToken(user, expirationTimeAtk)
    }

    /**
     * RefreshToken 생성
     */
    fun createRtk(user: User): String {
        return this.createToken(user, expirationTimeRtk)
    }

    /**
     * 토큰 substring (Bearer 제거)
     */
    fun substringToken(value: String): String {
        return value.substring("Bearer ".length)
    }

    /**
     * 토큰 검증
     */
    fun validateToken(token: String): Result<Claims?> {
        return runCatching {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        }
    }

    /**
     * Claims에서 사용자 정보 추출
     */
    fun getUserAuth(claims: Claims): UserAuth {
        val userId = claims.subject
        val email = claims["email"] as String
        val nickname = claims["nickname"] as String
        val role = claims["role"] as String

        return UserAuth(userId = userId, email = email, nickname = nickname, role = role)
    }
}
