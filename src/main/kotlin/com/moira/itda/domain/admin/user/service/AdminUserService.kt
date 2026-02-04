package com.moira.itda.domain.admin.user.service

import com.moira.itda.domain.admin.user.dto.request.UserBanRequest
import com.moira.itda.domain.admin.user.dto.response.AdminUserBanResponse
import com.moira.itda.domain.admin.user.dto.response.AdminUserResponse
import com.moira.itda.domain.admin.user.mapper.AdminUserMapper
import com.moira.itda.global.entity.UserBanHistory
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminUserService(
    private val adminUserMapper: AdminUserMapper
) {
    /**
     * 어드민 페이지 > 유저 > 유저 목록 조회
     */
    @Transactional(readOnly = true)
    fun getUsers(): List<AdminUserResponse> {
        return adminUserMapper.selectUserList()
    }

    /**
     * 어드민 페이지 > 유저 > 계정 정지
     */
    @Transactional
    fun ban(userId: String, request: UserBanRequest) {
        // [1] 유효성 검사
        if (request.reason.isBlank())
            throw ItdaException(ErrorCode.INVALID_BAN_REASON)
        if (request.banDays < 1)
            throw ItdaException(ErrorCode.INVALID_BAN_DAYS)
        if (adminUserMapper.selectUserChk(userId = userId) < 1)
            throw ItdaException(ErrorCode.USER_NOT_FOUND)

        // [2] 유저 status 변경
        adminUserMapper.updateBannedStatus(userId = userId)

        // [3] UserBanHistory 저장
        val userBanHistory = UserBanHistory.fromBanRequest(userId = userId, request = request)
        adminUserMapper.insertUserBanHistory(userBanHistory = userBanHistory)
    }

    /**
     * 어드민 페이지 > 유저 > 계정 정지 > 계정 정지 이력 조회
     */
    @Transactional(readOnly = true)
    fun getBanHistories(userId: String): List<AdminUserBanResponse> {
        return adminUserMapper.selectUserBanHistoryList(userId = userId)
    }

    /**
     * 어드민 페이지 > 유저 > 어드민 권한 부여
     */
    @Transactional
    fun giveAdmin(userId: String) {
        adminUserMapper.updateAdminRole(userId = userId)
    }
}