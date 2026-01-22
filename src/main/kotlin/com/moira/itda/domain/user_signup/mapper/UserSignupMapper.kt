package com.moira.itda.domain.user_signup.mapper

import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserIdentifyCodeType
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserSignupMapper {
    /**
     * 회원가입 > name, ci값 유효 여부 조회
     */
    fun selectUserIdentifyCodeChk(phoneNumber: String, type: UserIdentifyCodeType, name: String, ci: String): Boolean

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