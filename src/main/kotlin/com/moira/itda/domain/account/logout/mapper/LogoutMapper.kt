package com.moira.itda.domain.account.logout.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface LogoutMapper {
    /**
     * 로그아웃 > RefreshToken 초기화
     */
    fun updateRefreshTokenNull(userId: String)
}