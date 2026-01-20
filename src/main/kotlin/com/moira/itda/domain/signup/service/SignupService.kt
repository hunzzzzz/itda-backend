package com.moira.itda.domain.signup.service

import com.moira.itda.domain.signup.component.IdentifyCodeGenerator
import com.moira.itda.domain.signup.component.NicknameGenerator
import com.moira.itda.domain.signup.dto.request.SignupIdentifyCheckRequest
import com.moira.itda.domain.signup.dto.request.SignupIdentifyRequest
import com.moira.itda.domain.signup.dto.request.SignupRequest
import com.moira.itda.domain.signup.dto.response.SignupIdentifyInfoResponse
import com.moira.itda.domain.signup.mapper.SignupMapper
import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserIdentifyCode
import com.moira.itda.global.entity.UserIdentifyCodeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.sms.SmsUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.*

@Service
class SignupService(
    private val encoder: PasswordEncoder,
    private val mapper: SignupMapper,
    private val smsUtil: SmsUtil
) {
    private val emailRegex =
        Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")
    private val phoneNumberRegex = Regex("^0\\d{8,10}$")

    /**
     * 회원가입 > 휴대폰 본인인증
     */
    @Transactional
    fun identify(request: SignupIdentifyRequest) {
        val phoneNumber = request.phoneNumber

        // [1] 휴대폰번호 유효성 검사
        if (!phoneNumberRegex.matches(phoneNumber)) {
            throw ItdaException(ErrorCode.INVALID_PHONE_NUMBER)
        }
        if (mapper.selectPhoneNumberChk(phoneNumber = phoneNumber)) {
            throw ItdaException(ErrorCode.USING_PHONE_NUMBER)
        }

        // [2] 6자리 인증번호 생성
        val code = IdentifyCodeGenerator.generate()

        // [3] 6자리 인증번호 저장
        val userIdentifyCode = UserIdentifyCode.from(
            phoneNumber = phoneNumber, code = code, type = UserIdentifyCodeType.SIGNUP
        )
        mapper.insertUserIdentifyCode(userIdentifyCode = userIdentifyCode)

        // [4] 휴대폰번호로 본인인증 메시지 전송
        smsUtil.sendSms(toNumber = phoneNumber, message = "[ITDA] 회원가입을 위한 인증번호 ${userIdentifyCode.code}를 입력해주세요.")
    }

    /**
     * 회원가입 > 휴대폰 본인인증 > 인증코드 확인
     */
    @Transactional
    fun checkIdentifyCode(request: SignupIdentifyCheckRequest): SignupIdentifyInfoResponse {
        // [1] UserSignupIdentifyCode 조회
        val userSignupIdentifyCode = mapper.selectUserIdentifyCode(
            phoneNumber = request.phoneNumber,
            type = UserIdentifyCodeType.SIGNUP
        )

        // [2] 코드가 만료된 경우
        if (userSignupIdentifyCode == null || userSignupIdentifyCode.expiredAt.isBefore(ZonedDateTime.now())) {
            throw ItdaException(ErrorCode.EXPIRED_IDENTIFY_CODE)
        }

        // [3] 코드가 일치하지 않는 경우 (입력값과 DB 저장된 값 비교)
        if (request.code != userSignupIdentifyCode.code) {
            throw ItdaException(ErrorCode.INCORRECT_IDENTIFY_CODE)
        }

        // [4] UserIdentifyCode에 name과 CI 값 update 후, expired_at을 30분 더 늘려준다.
        val name = "아직 실명이 지정되지 않은 사용자" // TODO
        val ci = UUID.randomUUID().toString() // TODO

        mapper.updateUserIdentifyCode(
            phoneNumber = request.phoneNumber,
            name = name,
            ci = ci,
            type = UserIdentifyCodeType.SIGNUP,
        )

        // [5] 사용자의 실명, 휴대폰번호, CI 값을 리턴
        return SignupIdentifyInfoResponse(name = name, phoneNumber = request.phoneNumber, ci = ci)
    }

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
                name = request.name,
                ci = request.ci
            )
        ) {
            throw ItdaException(ErrorCode.EXPIRED_SIGNUP_INFO)
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