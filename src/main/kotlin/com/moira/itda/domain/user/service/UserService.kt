package com.moira.itda.domain.user.service

import com.moira.itda.domain.user.component.IdentifyCodeGenerator
import com.moira.itda.domain.user.component.UserValidator
import com.moira.itda.domain.user.dto.request.LoginRequest
import com.moira.itda.domain.user.dto.request.SignupRequest
import com.moira.itda.domain.user.dto.response.LoginResponse
import com.moira.itda.domain.user.dto.response.MyPageResponse
import com.moira.itda.domain.user.dto.response.TokenRefreshResponse
import com.moira.itda.domain.user.mapper.UserMapper
import com.moira.itda.domain.user.dto.request.ProfileImageUpdateRequest
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.auth.component.JwtProvider
import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserSignupIdentifyCode
import com.moira.itda.global.entity.UserStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Service
class UserService(
    private val awsS3Handler: AwsS3Handler,
    private val cookieHandler: CookieHandler,
    private val encoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val loginHistoryService: LoginHistoryService,
    private val mapper: UserMapper,
    private val validator: UserValidator
) {
    /**
     * 회원가입 > 닉네임 중복 확인
     */
    @Transactional(readOnly = true)
    fun checkNickname(nickname: String) {
        // [1] 유효성 검사 (중복 체크)
        validator.validateNickname(nickname = nickname)
    }

    /**
     * 회원가입 > 본인인증
     */
    @Transactional
    fun identify(email: String) {
        // [1] 유효성 검사 (중복 체크)
        validator.validateEmail(email = email)

        // [2] 6자리 인증번호 생성
        val code = IdentifyCodeGenerator.generate()

        // [3] 6자리 인증번호 저장
        val userSignupIdentifyCode = UserSignupIdentifyCode.from(email = email, code = code)
        mapper.insertUserSignupIdentifyCode(userSignupIdentifyCode = userSignupIdentifyCode)

        // TODO: [4] 이메일 전송
    }

    /**
     * 회원가입 > 본인인증 > 코드 확인
     */
    fun checkIdentifyCode(email: String, code: String) {
        // [1] UserSignupIdentifyCode 조회
        val userSignupIdentifyCode = mapper.selectUserSignupIdentifyCode(email = email)

        // [2] 코드가 존재하지 않거나, 만료된 경우
        if (userSignupIdentifyCode == null || userSignupIdentifyCode.expiredAt.isBefore(ZonedDateTime.now())) {
            throw ItdaException(ErrorCode.EXPIRED_IDENTIFY_CODE)
        }

        // [3] 코드가 일치하지 않는 경우
        if (code != userSignupIdentifyCode.code) {
            throw ItdaException(ErrorCode.INCORRECT_IDENTIFY_CODE)
        }
    }

    /**
     * 회원가입
     */
    @Transactional
    fun signup(request: SignupRequest) {
        // [1] 유효성 검사
        validator.validateSignup(request = request)

        // [2] User 저장
        val user = User.fromRequest(request = request, encoder = encoder)
        mapper.insertUser(user = user)
    }

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
            loginHistoryService.fail(user = user, ipAddress = ipAddress, userAgent = userAgent)

            // 에러 처리
            throw ItdaException(ErrorCode.LOGIN_ERROR)
        }
        // [3-2] 계정이 정지된 유저가 로그인 시도 시, 로그인 실패 기록 저장 후 에러 처리
        else if (user.status == UserStatus.BANNED) {
            // 로그인 실패 기록 저장
            loginHistoryService.fail(user = user, ipAddress = ipAddress, userAgent = userAgent)

            // 계정 정지 기한 조회 후 문자열 포매팅
            val bannedUntil = mapper.selectBannedUntil(userId = user.id)
                .format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시 m분"))
            val newErrorCode = ErrorCode.BANNED_USER_CANNOT_LOGIN
            newErrorCode.message = newErrorCode.message.replace("%s", bannedUntil)

            // 에러 처리
            throw ItdaException(newErrorCode)
        }
        // [3-3] 그 외의 경우, 로그인 성공 기록 저장
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

    /**
     * 로그아웃
     */
    @Transactional
    fun logout(userId: String, httpRes: HttpServletResponse) {
        // [1] RefreshToken 제거 (DB)
        mapper.updateRefreshTokenNull(userId = userId)

        // [2] RefreshToken 제거 (쿠키)
        cookieHandler.removeRtkInCookie(response = httpRes)
    }

    /**
     * 토큰 재발급
     */
    @Transactional
    fun refresh(
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): TokenRefreshResponse {
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
                    val email = claims.get("email", String::class.java)
                    val user = mapper.selectUserByEmail(email = email)
                        ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)

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

                    return TokenRefreshResponse(accessToken = atk)
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

        return TokenRefreshResponse(accessToken = "")
    }

    /**
     * 마이페이지 > 내 프로필 조회
     */
    @Transactional(readOnly = true)
    fun getMyProfile(userId: String): MyPageResponse {
        // [1] 조회
        return mapper.selectMyPageResponse(userId = userId) ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)
    }

    /**
     * 마이페이지 > 프로필 사진 변경
     */
    @Transactional
    fun updateProfileImage(userId: String, request: ProfileImageUpdateRequest) {
        // [1] 기존 프로필 사진 URL 조회
        val currentImageUrl = mapper.selectCurrentFileUrl(userId = userId)

        // [2] 기존 프로필 사진 삭제 (AWS S3)
        if (currentImageUrl != null) {
            awsS3Handler.delete(fileUrl = currentImageUrl)
        }

        // [3] 파일 ID 수정
        mapper.updateFileId(userId = userId, fileId = request.fileId)
    }
}