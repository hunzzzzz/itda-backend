package com.moira.itda.domain.common.refresh.mapper

import com.moira.itda.global.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommonTokenRefreshMapper {
    /**
     * 토큰 재발급 > User 조회
     */
    fun selectUserById(userId: String): User?

    /**
     * 토큰 재발급 > RefreshToken 수정
     */
    fun updateRefreshToken(userId: String, rtk: String)
}