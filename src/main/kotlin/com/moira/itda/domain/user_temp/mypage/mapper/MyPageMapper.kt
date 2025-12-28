package com.moira.itda.domain.user_temp.mypage.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyPageMapper {
    /**
     * 마이페이지 > 비밀번호 변경 > 현재 비밀번호 조회
     */
    fun selectCurrentPassword(userId: String): String

    /**
     * 마이페이지 > 비밀번호 변경
     */
    fun updatePassword(userId: String, newPassword: String)
}