package com.moira.itda.domain.user_simple_profile.mapper

import com.moira.itda.domain.user_simple_profile.dto.response.UserSimpleProfileResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserSimpleProfileMapper {
    /**
     * 심플 프로필 조회
     */
    fun selectUserSimpleProfileResponse(userId: String): UserSimpleProfileResponse?
}