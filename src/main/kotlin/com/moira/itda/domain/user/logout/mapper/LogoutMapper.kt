package com.moira.itda.domain.user.logout.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface LogoutMapper {
    /**
     * 상단바 > 로그아웃 > RefreshToken 초기화
     */
    fun updateRefreshTokenNull(userId: String)
}