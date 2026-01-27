package com.moira.itda.domain.account.login.mapper

import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserLoginHistory
import org.apache.ibatis.annotations.Mapper
import java.time.ZonedDateTime

@Mapper
interface LoginMapper {
    /**
     * 로그인 > User 조회
     */
    fun selectUserByEmail(email: String): User?

    /**
     * 로그인 > 계정 정지 기한 조회
     */
    fun selectBannedUntil(userId: String): ZonedDateTime

    /**
     * 로그인 > RefreshToken 수정
     */
    fun updateRefreshToken(userId: String, rtk: String)

    /**
     * 로그인 > LoginHistory 저장
     */
    fun insertUserLoginHistory(userLoginHistory: UserLoginHistory)
}