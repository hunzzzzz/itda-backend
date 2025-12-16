package com.moira.itda.domain.user.signup.service

import com.moira.itda.domain.user.signup.dto.request.SignupRequest
import com.moira.itda.domain.user.signup.mapper.SignupMapper
import com.moira.itda.global.entity.User
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignupService(
    private val passwordEncoder: PasswordEncoder,
    private val signupMapper: SignupMapper
) {
    private val emailRegex =
        Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")

    /**
     * 회원가입 > 닉네임 중복 확인
     */
    @Transactional(readOnly = true)
    fun checkNickname(nickname: String) {
        if (signupMapper.selectNicknameChk(nickname = nickname) > 0) {
            throw ItdaException(ErrorCode.ALREADY_USING_NICKNAME)
        }
    }

    /**
     * TODO: 회원가입 > 이메일 본인인증
     */
    @Transactional(readOnly = true)
    fun identify(email: String) {
        // [1] 유효성 검사
        if (signupMapper.selectEmailChk(email = email) > 0) {
            throw ItdaException(ErrorCode.ALREADY_USING_EMAIL)
        }
    }

    /**
     * 회원가입 > 유효성 검사
     */
    private fun validate(request: SignupRequest) {
        // 필드값 체크
        if (request.email.isBlank() || !emailRegex.matches(request.email)) {
            throw ItdaException(ErrorCode.INVALID_EMAIL)
        }
        if (request.password.isBlank() || !passwordRegex.matches(request.password)) {
            throw ItdaException(ErrorCode.INVALID_PASSWORD)
        }
        if (request.name.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_NAME)
        }
        if (request.nickname.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_NICKNAME)
        }

        // 중복 체크
        if (signupMapper.selectNicknameChk(nickname = request.nickname) > 0) {
            throw ItdaException(ErrorCode.ALREADY_USING_NICKNAME)
        }
        if (signupMapper.selectEmailChk(email = request.email) > 0) {
            throw ItdaException(ErrorCode.ALREADY_USING_EMAIL)
        }
    }

    /**
     * 회원가입
     */
    @Transactional
    fun signup(request: SignupRequest) {
        // [1] 유효성 검사
        this.validate(request = request)

        // [2] User 저장
        val user = User.fromSignupRequest(request = request, passwordEncoder = passwordEncoder)
        signupMapper.insertUser(user = user)
    }
}