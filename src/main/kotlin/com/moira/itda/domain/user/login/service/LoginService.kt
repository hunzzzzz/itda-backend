package com.moira.itda.domain.user.login.service

import com.moira.itda.domain.user.login.dto.request.LoginRequest
import com.moira.itda.domain.user.login.dto.response.LoginResponse
import com.moira.itda.domain.user.login.mapper.LoginMapper
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.auth.component.JwtProvider
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
    private val loginHistoryService: LoginHistoryService,
    private val loginMapper: LoginMapper,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder
) {
    /**
     * 로그인
     */
    @Transactional
    fun login(
        request: LoginRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): LoginResponse {
        // [1] 변수 세팅
        val ipAddress = httpServletRequest.remoteAddr
        val userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT)

        // [2] 이메일 존재 여부 확인
        if (loginMapper.selectEmailChk(email = request.email) < 1) {
            throw ItdaException(ErrorCode.LOGIN_ERROR)
        }

        // [3] 이메일로 User 조회
        val user = loginMapper.selectUser(email = request.email)
            ?: throw ItdaException(ErrorCode.LOGIN_ERROR)

        // [4-1] 비밀번호 불일치 시, 로그인 실패 기록 저장 후 에러 처리
        if (!passwordEncoder.matches(request.password, user.password)) {
            // 로그인 실패 기록 저장
            loginHistoryService.saveFailedLoginHistory(user = user, ipAddress = ipAddress, userAgent = userAgent)

            // 에러 처리
            throw ItdaException(ErrorCode.LOGIN_ERROR)
        }
        // [4-2] 계정이 정지된 유저가 로그인 시도 시, 로그인 실패 기록 저장 후 에러 처리
        else if (user.status == UserStatus.BANNED) {
            // 로그인 실패 기록 저장
            loginHistoryService.saveFailedLoginHistory(user = user, ipAddress = ipAddress, userAgent = userAgent)

            // 계정 정지 기한 조회 후 문자열 포매팅
            val bannedUntil = loginMapper.selectBannedUntil(userId = user.id)
            val formattedBannedUntil = bannedUntil.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시 m분"))
            val newErrorCode = ErrorCode.BANNED_USER_CANNOT_LOGIN
            newErrorCode.message = newErrorCode.message.replace("%s", formattedBannedUntil)

            // 에러 처리
            throw ItdaException(newErrorCode)
        }
        // [4-3] 그 외의 경우, 로그인 성공 기록 저장
        else {
            loginHistoryService.saveSuccessLoginHistory(user = user, ipAddress = ipAddress, userAgent = userAgent)
        }

        // [5] JWT 토큰 생성
        val atk = jwtProvider.createAtk(user = user)
        val rtk = jwtProvider.createRtk(user = user)

        // [6] RefreshToken 저장 (DB, 쿠키)
        loginMapper.updateRefreshToken(userId = user.id, rtk = rtk)
        cookieHandler.putRtkInCookie(rtk = rtk, response = httpServletResponse)

        // [7] AccessToken 리턴
        return LoginResponse(accessToken = atk)
    }
}