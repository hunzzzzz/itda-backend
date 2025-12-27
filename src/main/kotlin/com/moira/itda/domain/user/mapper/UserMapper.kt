package com.moira.itda.domain.user.mapper

import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserSignupIdentifyCode
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    /**
     * 회원가입 > 닉네임 중복 확인
     */
    fun selectNicknameChk(nickname: String): Int

    /**
     * 회원가입 > 이메일 중복 확인
     */
    fun selectEmailChk(email: String): Int

    /**
     * 회원가입 > 본인인증 > UserSignupIdentifyCode 저장
     */
    fun insertUserSignupIdentifyCode(userSignupIdentifyCode: UserSignupIdentifyCode)

    /**
     * 회원가입 > 본인인증 > UserSignupIdentifyCode 조회
     */
    fun selectUserSignupIdentifyCode(email: String): UserSignupIdentifyCode?
    
    /**
     * 회원가입 > User 저장
     */
    fun insertUser(user: User)

    // ---------------------------------------------------------------------------------------------- //

    /**
     * 스케쥴러 (30분 간격) > UserSignupIdentifyCode 삭제
     */
    fun deleteUserSignupIdentifyCode()
}