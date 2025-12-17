package com.moira.itda.domain.token.mapper

import com.moira.itda.global.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TokenMapper {
    /**
     * 토큰 재발급 > User 조회 (by 유저 ID)
     */
    fun selectUser(userId: String): User?

    /**
     * 토큰 재발급 > RefreshToken 저장
     */
    fun updateRefreshToken(userId: String, rtk: String)
}