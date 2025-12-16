package com.moira.itda.domain.user.signup.mapper

import com.moira.itda.global.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SignupMapper {
    /**
     * 회원가입 > 닉네임 중복 확인
     */
    fun selectNicknameChk(nickname: String): Int

    /**
     * 회원가입 > 이메일 중복 확인
     */
    fun selectEmailChk(email: String): Int

    /**
     * 회원가입 > User 저장
     */
    fun insertUser(user: User)
}