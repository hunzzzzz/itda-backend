package com.moira.itda.domain.signup.mapper

import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserIdentifyCode
import com.moira.itda.global.entity.UserIdentifyCodeType
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SignupMapper {
    /**
     * 회원가입 > 휴대폰 본인인증 > 휴대폰 중복 체크
     */
    fun selectPhoneNumberChk(phoneNumber: String): Boolean

    /**
     * 회원가입 > 휴대폰 본인인증 > UserIdentifyCode 저장
     */
    fun insertUserIdentifyCode(userIdentifyCode: UserIdentifyCode)

    /**
     * 회원가입 > 휴대폰 본인인증 > 인증코드 확인 > UserIdentifyCode 조회
     */
    fun selectUserIdentifyCode(phoneNumber: String, type: UserIdentifyCodeType): UserIdentifyCode?

    /**
     * 회원가입 > 휴대폰 본인인증 > 인증코드 확인 > UserIdentifyCode 수정
     */
    fun updateUserIdentifyCode(phoneNumber: String, name: String, ci: String, type: UserIdentifyCodeType)

    /**
     * 회원가입 > name, ci값 유효 여부 조회
     */
    fun selectUserIdentifyCodeChk(phoneNumber: String, name: String, ci: String): Boolean

    /**
     * 회원가입 > 이메일 중복 확인
     */
    fun selectEmailChk(email: String): Boolean

    /**
     * 회원가입 > 닉네임 중복 확인
     */
    fun selectNicknameChk(nickname: String): Boolean

    /**
     * 회원가입 > User 저장
     */
    fun insertUser(user: User)
}