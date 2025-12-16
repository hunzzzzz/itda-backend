package com.moira.itda.global.auth.component

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
class JwtProvider {
    companion object {
        private const val JWT_ISSUER = "ITDA" // 토큰 발급자
        private const val JWT_EXPIRATION_TIME_ATK = 1000 * 60 * 60 * 2L  // ATK 유효시간 (2시간)
        private const val JWT_EXPIRATION_TIME_RTK = 1000 * 60 * 60 * 24L // RTK 유효시간 (24시간)
    }

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
            .issuer(JWT_ISSUER)
            .signWith(key)
            .compact()
    }

    /**
     * AccessToken 생성
     */
    fun createAtk(user: User): String {
        return this.createToken(user, JWT_EXPIRATION_TIME_ATK)
    }

    /**
     * RefreshToken 생성
     */
    fun createRtk(user: User): String {
        return this.createToken(user, JWT_EXPIRATION_TIME_RTK)
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
    fun validateToken(atk: String): Result<Claims?> {
        return runCatching {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(atk).payload
        }
    }
}
