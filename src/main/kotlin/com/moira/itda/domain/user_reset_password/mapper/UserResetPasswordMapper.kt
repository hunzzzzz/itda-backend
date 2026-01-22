package com.moira.itda.domain.user_reset_password.mapper

import com.moira.itda.global.entity.UserIdentifyCodeType
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserResetPasswordMapper {
    /**
     * 비밀번호 초기화 > name, ci값 유효 여부 조회
     */
    fun selectUserIdentifyCodeChk(phoneNumber: String, type: UserIdentifyCodeType, name: String, ci: String): Boolean

    /**
     * 비밀번호 초기화
     */
    fun updatePasswordByPhoneNumber(phoneNumber: String, newPassword: String, name: String, ci: String)
}