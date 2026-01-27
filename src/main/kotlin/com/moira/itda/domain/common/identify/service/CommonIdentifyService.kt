package com.moira.itda.domain.common.identify.service

import com.moira.itda.domain.common.identify.dto.request.IdentifyCheckRequest
import com.moira.itda.domain.common.identify.dto.request.IdentifyRequest
import com.moira.itda.domain.common.identify.dto.response.IdentifyResponse
import com.moira.itda.domain.common.identify.mapper.CommonIdentifyMapper
import com.moira.itda.domain.account.signup.component.IdentifyCodeGenerator
import com.moira.itda.global.entity.UserIdentifyCode
import com.moira.itda.global.entity.UserIdentifyCodeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.sms.SmsUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.*

@Service
class CommonIdentifyService(
    private val mapper: CommonIdentifyMapper,
    private val smsUtil: SmsUtil
) {
    private val phoneNumberRegex = Regex("^0\\d{8,10}$")

    /**
     * 본인인증
     */
    @Transactional
    fun identify(request: IdentifyRequest) {
        val phoneNumber = request.phoneNumber

        // [1] 유효성 검사
        if (request.phoneNumber.contains("-") || !phoneNumberRegex.matches(request.phoneNumber)) {
            throw ItdaException(ErrorCode.INVALID_PHONE_NUMBER)
        }
        when (request.type) {
            // 회원가입 시, 사용자가 입력한 휴대폰번호가 DB 상에 존재하면 안된다.
            UserIdentifyCodeType.SIGNUP -> {
                if (mapper.selectPhoneNumberChk(phoneNumber = phoneNumber)) {
                    throw ItdaException(ErrorCode.USING_PHONE_NUMBER)
                }
            }
            // 기타 경우, 사용자가 입력한 휴대폰번호가 DB 상에 존재해야 한다.
            else -> {
                if (!mapper.selectPhoneNumberChk(phoneNumber = phoneNumber)) {
                    throw ItdaException(ErrorCode.NON_EXISTING_PHONE)
                }
            }
        }

        // [2] 변수 세팅
        val code = IdentifyCodeGenerator.generate()
        val smsMessage = "[ITDA] 본인인증을 위한 인증코드 [${code}]를 입력해주세요."

        // [3] 6자리 인증번호 저장
        val userIdentifyCode = UserIdentifyCode.from(
            phoneNumber = phoneNumber, code = code, type = request.type
        )
        mapper.insertUserIdentifyCode(userIdentifyCode = userIdentifyCode)

        // [4] 휴대폰번호로 본인인증 메시지 전송
        smsUtil.sendSms(toNumber = phoneNumber, message = smsMessage)
    }

    /**
     * 본인인증 > 인증코드 확인
     */
    @Transactional
    fun checkIdentifyCode(request: IdentifyCheckRequest): IdentifyResponse? {
        val phoneNumber = request.phoneNumber

        // [1] UserIdentifyCode 조회
        val userIdentifyCode = mapper.selectUserIdentifyCode(
            phoneNumber = phoneNumber,
            type = request.type
        )

        // [2] 코드가 만료된 경우
        if (userIdentifyCode == null || userIdentifyCode.expiredAt.isBefore(ZonedDateTime.now())) {
            throw ItdaException(ErrorCode.EXPIRED_IDENTIFY_CODE)
        }

        // [3] 코드가 일치하지 않는 경우 (입력값과 DB 저장된 값 비교)
        if (request.code != userIdentifyCode.code) {
            throw ItdaException(ErrorCode.INCORRECT_IDENTIFY_CODE)
        }

        // [4] UserIdentifyCode에 name과 CI 값 update 후, expired_at을 30분 더 늘려준다.
        // 회원가입의 경우 name과 ci를 초기화, 나머지 경우에는 기존 User의 name과 ci를 조회
        var userInfo: HashMap<String, String?>? = null

        if (request.type != UserIdentifyCodeType.SIGNUP) {
            userInfo = mapper.selectUserInfo(phoneNumber = phoneNumber)
        }

        val name = userInfo?.get("name") ?: "아직 실명이 지정되지 않은 사용자" // TODO
        val ci = userInfo?.get("ci") ?: UUID.randomUUID().toString() // TODO

        mapper.updateUserIdentifyCode(
            phoneNumber = phoneNumber,
            name = name,
            ci = ci,
            type = request.type,
        )

        // [5] 사용자의 실명, 휴대폰번호, CI 값을 리턴
        return IdentifyResponse(name = name, phoneNumber = phoneNumber, ci = ci)
    }
}