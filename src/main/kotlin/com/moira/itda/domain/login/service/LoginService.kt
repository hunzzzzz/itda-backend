package com.moira.itda.domain.login.service

import com.moira.itda.domain.login.dto.request.LoginRequest
import com.moira.itda.domain.login.dto.response.LoginResponse
import com.moira.itda.domain.login.mapper.LoginMapper
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.auth.component.JwtProvider
import com.moira.itda.global.entity.UserLoginFailReason
import com.moira.itda.global.entity.UserStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class LoginService(
    private val cookieHandler: CookieHandler,
    private val encoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val loginHistoryService: LoginHistoryService,
    private val mapper: LoginMapper
) {
    /**
     * 로그인
     */
    @Transactional
    fun login(
        request: LoginRequest,
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): LoginResponse {
        // [1] 변수 세팅
        val ipAddress = httpReq.remoteAddr
        val userAgent = httpReq.getHeader(HttpHeaders.USER_AGENT)

        // [2] 이메일로 User 조회
        val user = mapper.selectUserByEmail(email = request.email)
            ?: throw ItdaException(ErrorCode.LOGIN_ERROR)

        // [3-1] 비밀번호 불일치 시, 로그인 실패 기록 저장 후 에러 처리
        if (!encoder.matches(request.password, user.password)) {
            // 로그인 실패 기록 저장
            loginHistoryService.fail(
                user = user,
                ipAddress = ipAddress,
                userAgent = userAgent,
                failReason = UserLoginFailReason.WRONG_PASSWORD
            )

            // 에러 처리
            throw ItdaException(ErrorCode.LOGIN_ERROR)
        }
        // [3-2] 계정이 정지된 유저가 로그인 시도 시, 로그인 실패 기록 저장 후 에러 처리
        else if (user.status == UserStatus.BANNED) {
            // 로그인 실패 기록 저장
            loginHistoryService.fail(
                user = user,
                ipAddress = ipAddress,
                userAgent = userAgent,
                failReason = UserLoginFailReason.BANNED_USER
            )

            // 계정 정지 기한 조회 후 문자열 포매팅
            val bannedUntil = mapper.selectBannedUntil(userId = user.id)
                .format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시 m분"))
            val newErrorCode = ErrorCode.BANNED_USER_CANNOT_LOGIN
            newErrorCode.message = newErrorCode.message.replace("%s", bannedUntil)

            // 에러 처리
            throw ItdaException(newErrorCode)
        }
        // [3-3] 회원탈퇴한 유저가 로그인 시도 시, 로그인 실패 기록 저장 후 에러 처리
        else if (user.status == UserStatus.DELETED) {
            // 로그인 실패 기록 저장
            loginHistoryService.fail(
                user = user,
                ipAddress = ipAddress,
                userAgent = userAgent,
                failReason = UserLoginFailReason.DELETED_USER
            )

            // 에러 처리
            throw ItdaException(ErrorCode.DELETED_USER_CANNOT_LOGIN)
        }
        // [3-4] 그 외의 경우, 로그인 성공 기록 저장
        else {
            loginHistoryService.success(user = user, ipAddress = ipAddress, userAgent = userAgent)
        }

        // [4] JWT 토큰 생성
        val atk = jwtProvider.createAtk(user = user)
        val rtk = jwtProvider.createRtk(user = user)

        // [5] RefreshToken 저장 (DB, 쿠키)
        mapper.updateRefreshToken(userId = user.id, rtk = rtk)
        cookieHandler.putRtkInCookie(rtk = rtk, response = httpRes)

        // [6] AccessToken 리턴
        return LoginResponse(accessToken = atk)
    }
}