package com.moira.itda.domain.user_simple_profile.service

import com.moira.itda.domain.user_simple_profile.dto.response.UserSimpleProfileResponse
import com.moira.itda.domain.user_simple_profile.mapper.UserSimpleProfileMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSimpleProfileService(
    private val mapper: UserSimpleProfileMapper
) {
    /**
     * 심플 프로필 조회
     */
    @Transactional(readOnly = true)
    fun getSimpleUserProfile(userId: String): UserSimpleProfileResponse {
        return mapper.selectUserSimpleProfileResponse(userId = userId)
            ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)
    }
}