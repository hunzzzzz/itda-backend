package com.moira.itda.domain.account.signup.service

import com.moira.itda.domain.account.signup.component.NicknameGenerator
import com.moira.itda.domain.account.signup.dto.request.SignupRequest
import com.moira.itda.domain.account.signup.mapper.SignupMapper
import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserIdentifyCodeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SignupService(
    private val encoder: PasswordEncoder,
    private val mapper: SignupMapper
) {
    private val emailRegex =
        Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")
    private val phoneNumberRegex = Regex("^0\\d{8,10}$")

    /**
     * 회원가입 > 이메일 중복 확인
     */
    @Transactional(readOnly = true)
    fun checkEmailDuplication(email: String) {
        // [1] 이메일 중복 확인
        if (mapper.selectEmailChk(email = email)) {
            throw ItdaException(ErrorCode.USING_EMAIL)
        }
    }

    /**
     * 회원가입 > 유효성 검사
     */
    private fun validateSignup(request: SignupRequest) {
        // 휴대폰번호에 대한 실명, CI 값 존재 여부 확인
        if (!mapper.selectUserIdentifyCodeChk(
                phoneNumber = request.phoneNumber,
                type = UserIdentifyCodeType.SIGNUP,
                name = request.name,
                ci = request.ci
            )
        ) {
            throw ItdaException(ErrorCode.EXPIRED_IDENTIFY_INFO)
        }
        // 이메일 정규식 유효성 검사
        if (!emailRegex.matches(request.email)) {
            throw ItdaException(ErrorCode.INVALID_EMAIL)
        }
        // 이메일 중복 확인
        if (mapper.selectEmailChk(email = request.email)) {
            throw ItdaException(ErrorCode.USING_EMAIL)
        }
        // 비밀번호 정규식 유효성 검사
        if (!passwordRegex.matches(request.password)) {
            throw ItdaException(ErrorCode.INVALID_PASSWORD)
        }
        // 휴대폰 번호 정규식 유효성 검사
        if (request.phoneNumber.contains("-") || !phoneNumberRegex.matches(request.phoneNumber)) {
            throw ItdaException(ErrorCode.INVALID_PHONE_NUMBER)
        }
    }

    /**
     * 회원가입
     */
    @Transactional
    fun signup(request: SignupRequest) {
        // [1] 유효성 검사
        this.validateSignup(request = request)

        // [2] 닉네임 생성
        var randomNickname = NicknameGenerator.generate()
        var retryCount = 0

        // 닉네임이 중복되지 않을때 까지 while문 반복
        while (mapper.selectNicknameChk(nickname = randomNickname) && retryCount < 10) {
            randomNickname = NicknameGenerator.generate()
            retryCount++
        }
        // 단, 10번 시도 후에도 중복이면 UUID를 붙여서라도 고유성을 확보한다.
        if (retryCount >= 10) {
            randomNickname = "${randomNickname}_${UUID.randomUUID().toString().substring(0, 4)}"
        }

        // [3] User 저장
        val user = User.fromRequest(request = request, randomNickname = randomNickname, encoder = encoder)
        mapper.insertUser(user = user)
    }
}