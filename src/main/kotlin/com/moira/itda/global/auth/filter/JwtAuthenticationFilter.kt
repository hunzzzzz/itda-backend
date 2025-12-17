package com.moira.itda.global.auth.filter

import com.moira.itda.global.auth.component.FilterErrorSender
import com.moira.itda.global.auth.component.JwtProvider
import com.moira.itda.global.auth.dto.UserAuth
import com.moira.itda.global.exception.ErrorCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val filterErrorSender: FilterErrorSender,
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
    companion object {
        private val excludeRequestMatchers = listOf(
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/signup/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/signup/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/login/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/token/refresh/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/gacha")
        )
    }

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 필터를 통과시키지 않을 API 엔드포인트 정의
     */
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return excludeRequestMatchers.any { it.matches(request) }
    }

    /**
     * Jwt 토큰 검증 필터 구현
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("[요청 URI] {} {}", request.method, request.requestURI)

        // [1] Authorization 헤더 추출 및 검증
        val authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith("Bearer ")) {
            filterErrorSender.sendErrorResponse(response = response, errorCode = ErrorCode.INVALID_AUTHORIZATION_HEADER)
            return
        }

        // [2] AccessToken 추출
        val accessToken = jwtProvider.substringToken(authorizationHeaderValue)

        // [3] AccessToken 검증
        jwtProvider.validateToken(accessToken)
            // [3-1] 검증 성공 시, 다음 로직 수행
            .onSuccess { claims ->
                if (claims != null) {
                    // [4] 유저 정보 추출
                    val userId = claims.subject
                    val email = claims["email"] as String
                    val nickname = claims["nickname"] as String
                    val role = claims["role"] as String
                    val userAuth = UserAuth(userId = userId, email = email, nickname = nickname, role = role)

                    // [5] Spring Security 권한 및 Authentication 객체 생성
                    val authorities = listOf("ROLE_${role}").map { SimpleGrantedAuthority(it) }
                    val authentication = UsernamePasswordAuthenticationToken(userAuth, null, authorities)
                    SecurityContextHolder.getContext().authentication = authentication

                    filterChain.doFilter(request, response)
                }
            }
            // [3-2] 검증 실패 시, 에러 처리
            .onFailure {
                val errorCode = when (it) {
                    is ExpiredJwtException -> ErrorCode.EXPIRED_ATK
                    is SignatureException -> ErrorCode.INVALID_SIGNATURE
                    is UnsupportedJwtException, is MalformedJwtException -> ErrorCode.INVALID_TOKEN
                    else -> {
                        log.error("[ItdaApplication] 에러 발생! {}", it.message)
                        ErrorCode.INTERNAL_SERVER_ERROR
                    }
                }

                filterErrorSender.sendErrorResponse(response = response, errorCode = errorCode)
            }
    }
}