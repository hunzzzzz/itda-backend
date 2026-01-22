package com.moira.itda.domain.token_refresh.service

import com.moira.itda.domain.token_refresh.dto.response.TokenRefreshResponse
import com.moira.itda.domain.token_refresh.mapper.TokenRefreshMapper
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.auth.component.JwtProvider
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenRefreshService(
    private val cookieHandler: CookieHandler,
    private val jwtProvider: JwtProvider,
    private val mapper: TokenRefreshMapper
) {
    /**
     * 토큰 재발급
     */
    @Transactional
    fun refresh(
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): TokenRefreshResponse? {
        // [1] Authorization 헤더값 추출
        val authorizationHeaderValue = httpReq.getHeader("Authorization")
            ?: throw ItdaException(ErrorCode.INVALID_AUTHORIZATION_HEADER)

        if (!authorizationHeaderValue.startsWith("Bearer ")) {
            throw ItdaException(ErrorCode.INVALID_TOKEN)
        }

        // [2] RefreshToken 추출
        val refreshToken = jwtProvider.substringToken(authorizationHeaderValue)

        jwtProvider.validateToken(refreshToken)
            .onSuccess { claims ->
                if (claims != null) {
                    // [3] 유저 정보 추출
                    val userId = claims.subject
                    val user = mapper.selectUserById(userId = userId)
                        ?: throw ItdaException(ErrorCode.ACCESS_DENIED)

                    // [4] 토큰 검증 (with db)
                    val rtkInDb = user.refreshToken ?: throw ItdaException(ErrorCode.EXPIRED_USER_INFO)
                    if (refreshToken != rtkInDb) {
                        throw ItdaException(ErrorCode.INVALID_TOKEN)
                    }

                    // [5] 토큰 재발급
                    val atk = jwtProvider.createAtk(user = user)
                    val rtk = jwtProvider.createRtk(user = user)

                    // [6] RTK 저장
                    mapper.updateRefreshToken(userId = userId, rtk = rtk)
                    cookieHandler.putRtkInCookie(rtk = rtk, response = httpRes)

                    return TokenRefreshResponse(accessToken = atk, refreshToken = rtk)
                }
            }
            .onFailure {
                val errorCode = when (it) {
                    is ExpiredJwtException -> ErrorCode.EXPIRED_USER_INFO
                    is SignatureException -> ErrorCode.INVALID_SIGNATURE
                    is UnsupportedJwtException, is MalformedJwtException -> ErrorCode.INVALID_TOKEN
                    else -> ErrorCode.INTERNAL_SERVER_ERROR
                }

                throw ItdaException(errorCode = errorCode)
            }

        return null
    }
}