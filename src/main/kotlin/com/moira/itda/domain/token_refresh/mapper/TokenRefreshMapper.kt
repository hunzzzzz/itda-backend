package com.moira.itda.domain.token_refresh.mapper

import com.moira.itda.global.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TokenRefreshMapper {
    /**
     * 토큰 재발급 > User 조회
     */
    fun selectUserById(userId: String): User?

    /**
     * 토큰 재발급 > RefreshToken 수정
     */
    fun updateRefreshToken(userId: String, rtk: String)
}