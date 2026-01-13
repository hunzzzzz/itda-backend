package com.moira.itda.global.auth.filter

import com.moira.itda.global.auth.component.JwtProvider
import com.moira.itda.global.exception.ErrorCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageDeliveryException
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class StompHandler(
    private val jwtProvider: JwtProvider
) : ChannelInterceptor {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java) ?: return message

        // [1] 연결 시도 (CONNECT) 시점에만 토큰 검증 수행
        if (StompCommand.CONNECT == accessor.command) {
            val authorizationHeaderValue = accessor.getFirstNativeHeader("Authorization")

            if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith("Bearer ")) {
                throw MessageDeliveryException(ErrorCode.INVALID_AUTHORIZATION_HEADER.message)
            }

            // [2] AccessToken 추출
            val accessToken = jwtProvider.substringToken(authorizationHeaderValue)

            // [3] AccessToken 검증
            jwtProvider.validateToken(accessToken)
                // [3-1] 검증 성공 시, 다음 로직 수행
                .onSuccess { claims ->
                    if (claims != null) {
                        // [4] 유저 정보 추출
                        val userAuth = jwtProvider.getUserAuth(claims = claims)

                        // [5] Spring Security 권한 및 Authentication 객체 생성
                        val authorities = listOf("ROLE_${userAuth.role}").map { SimpleGrantedAuthority(it) }
                        val authentication = UsernamePasswordAuthenticationToken(userAuth, null, authorities)
                        SecurityContextHolder.getContext().authentication = authentication

                        // [6] STOMP accessor에 인증 정보를 세팅
                        accessor.user = authentication
                        log.info("[StompHandler] WebSocket 인증 성공: {}", userAuth.userId)
                    }
                }
                // [3-2] 검증 실패 시, 에러 처리
                .onFailure {
                    val errorCode = when (it) {
                        is ExpiredJwtException -> ErrorCode.EXPIRED_ATK
                        is SignatureException -> ErrorCode.INVALID_SIGNATURE
                        is UnsupportedJwtException, is MalformedJwtException -> ErrorCode.INVALID_TOKEN
                        else -> ErrorCode.INTERNAL_SERVER_ERROR
                    }

                    log.error("[StompHandler: {}] WebSocket 인증 오류 발생! {}", errorCode, it.message)

                    throw MessageDeliveryException(errorCode.message)
                }
        }

        return super.preSend(message, channel)
    }
}