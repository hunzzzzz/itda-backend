package com.moira.itda.domain.user.component

import com.moira.itda.domain.user.mapper.UserMapper
import com.moira.itda.domain.user.request.SignupRequest
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val mapper: UserMapper,
) {
    private val emailRegex =
        Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")

    /**
     * 회원가입 > 닉네임 중복 확인
     */
    fun validateNickname(nickname: String) {
        if (mapper.selectNicknameChk(nickname = nickname) > 0) {
            throw ItdaException(ErrorCode.USING_NICKNAME)
        }
    }

    /**
     * 회원가입 > 이메일 중복 확인
     */
    fun validateEmail(email: String) {
        if (mapper.selectEmailChk(email = email) > 0) {
            throw ItdaException(ErrorCode.USING_EMAIL)
        }
    }

    /**
     * 회원가입 > 유효성 검사
     */
    fun validateSignup(request: SignupRequest) {
        // 이메일
        if (request.email.isBlank()) {
            throw ItdaException(ErrorCode.NO_EMAIL)
        }
        if (!emailRegex.matches(request.email)) {
            throw ItdaException(ErrorCode.INVALID_EMAIL)
        }
        if (mapper.selectEmailChk(email = request.email) > 0) {
            throw ItdaException(ErrorCode.USING_EMAIL)
        }
        // 비밀번호
        if (request.password.isBlank()) {
            throw ItdaException(ErrorCode.NO_PASSWORD)
        }
        if (!passwordRegex.matches(request.password)) {
            throw ItdaException(ErrorCode.INVALID_PASSWORD)
        }
        // 이름
        if (request.name.isBlank()) {
            throw ItdaException(ErrorCode.NO_USER_NAME)
        }
        // 닉네임
        if (request.nickname.isBlank()) {
            throw ItdaException(ErrorCode.NO_NICKNAME)
        }
        if (mapper.selectNicknameChk(nickname = request.nickname) > 0) {
            throw ItdaException(ErrorCode.USING_NICKNAME)
        }
    }
}