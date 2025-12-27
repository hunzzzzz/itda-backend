package com.moira.itda.domain.user.service

import com.moira.itda.domain.user.component.IdentifyCodeGenerator
import com.moira.itda.domain.user.component.UserValidator
import com.moira.itda.domain.user.mapper.UserMapper
import com.moira.itda.domain.user.request.SignupRequest
import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserSignupIdentifyCode
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class UserService(
    private val encoder: PasswordEncoder,
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
        val user = User.fromRequest(
            request = request,
            encoder = encoder
        )
        mapper.insertUser(user = user)
    }
}