package com.moira.itda.domain.user_temp.login.mapper

import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserLoginHistory
import org.apache.ibatis.annotations.Mapper
import java.time.ZonedDateTime

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
     * 로그인 > 계정 정지 기한 조회
     */
    fun selectBannedUntil(userId: String): ZonedDateTime

    /**
     * 로그인 > User 수정
     */
    fun updateRefreshToken(userId: String, rtk: String)

    /**
     * 로그인 > LoginHistory 저장
     */
    fun insertUserLoginHistory(userLoginHistory: UserLoginHistory)
}