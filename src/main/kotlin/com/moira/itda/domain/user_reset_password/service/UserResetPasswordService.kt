package com.moira.itda.domain.user_reset_password.service

import com.moira.itda.domain.user_reset_password.dto.request.ResetPasswordRequest
import com.moira.itda.domain.user_reset_password.mapper.UserResetPasswordMapper
import com.moira.itda.global.entity.UserIdentifyCodeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserResetPasswordService(
    private val encoder: PasswordEncoder,
    private val mapper: UserResetPasswordMapper
) {
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")

    /**
     * 비밀번호 초기화
     */
    @Transactional
    fun resetPassword(request: ResetPasswordRequest) {
        // [1] 휴대폰번호에 대한 실명, CI 값 존재 여부 확인
        if (!mapper.selectUserIdentifyCodeChk(
                phoneNumber = request.phoneNumber,
                type = UserIdentifyCodeType.RESET_PASSWORD,
                name = request.name,
                ci = request.ci
            )
        ) {
            throw ItdaException(ErrorCode.EXPIRED_IDENTIFY_INFO)
        }

        // [2] 새로운 비밀번호 유효성 검사
        if (!passwordRegex.matches(request.newPassword)) {
            throw ItdaException(ErrorCode.INVALID_PASSWORD)
        }

        // [3] 비밀번호 변경
        mapper.updatePasswordByPhoneNumber(
            phoneNumber = request.phoneNumber,
            newPassword = encoder.encode(request.newPassword),
            name = request.name,
            ci = request.ci
        )
    }
}