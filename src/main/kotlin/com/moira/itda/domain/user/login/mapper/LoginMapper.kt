package com.moira.itda.domain.user.login.mapper

import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserLoginHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface LoginMapper {
    /**
     * 로그인 > 이메일 존재 여부 조회
     */
    fun selectEmailChk(email: String): Int

    /**
     * 로그인 > User 조회 (by 이메일)
     */
    fun selectUser(email: String): User?

    /**
     * 로그인 > User 수정
     */
    fun updateUser(userId: String, rtk: String)

    /**
     * 로그인 > LoginHistory 저장
     */
    fun insertUserLoginHistory(userLoginHistory: UserLoginHistory)
}