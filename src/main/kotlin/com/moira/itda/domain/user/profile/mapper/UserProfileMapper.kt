package com.moira.itda.domain.user.profile.mapper

import com.moira.itda.domain.user.profile.dto.response.UserProfileResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserProfileMapper {
    /**
     * 프로필 조회
     */
    fun selectUserProfileResponse(userId: String): UserProfileResponse?
}