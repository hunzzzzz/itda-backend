package com.moira.itda.domain.admin.user.mapper

import com.moira.itda.domain.admin.user.dto.response.AdminUserBanResponse
import com.moira.itda.domain.admin.user.dto.response.AdminUserResponse
import com.moira.itda.global.entity.UserBanHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdminUserMapper {
    /**
     * 어드민 페이지 > 유저 > 유저 목록 조회
     */
    fun selectUserList(): List<AdminUserResponse>

    /**
     * 어드민 페이지 > 유저 > 계정 정지 > 유저 존재 여부 확인
     */
    fun selectUserChk(userId: String): Int
    
    /**
     * 어드민 페이지 > 유저 > 계정 정지 > status 변경
     */
    fun updateBannedStatus(userId: String)

    /**
     * 어드민 페이지 > 유저 > 계정 정지 > UserBanHistory 저장
     */
    fun insertUserBanHistory(userBanHistory: UserBanHistory)

    /**
     * 어드민 페이지 > 유저 > 계정 정지 > 계정 정지 이력 조회
     */
    fun selectUserBanHistoryList(userId: String): List<AdminUserBanResponse>

    /**
     * 어드민 페이지 > 유저 > 어드민 권한 부여
     */
    fun updateAdminRole(userId: String)
}