package com.moira.itda.domain.user.profile.service

import com.moira.itda.domain.user.profile.dto.response.UserProfileResponse
import com.moira.itda.domain.user.profile.mapper.UserProfileMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserProfileService(
    private val mapper: UserProfileMapper
) {
    /**
     * 프로필 조회
     */
    @Transactional(readOnly = true)
    fun getUserProfile(userId: String): UserProfileResponse {
        return mapper.selectUserProfileResponse(userId = userId)
            ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)
    }
}