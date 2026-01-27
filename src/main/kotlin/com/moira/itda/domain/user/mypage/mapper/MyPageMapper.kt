package com.moira.itda.domain.user.mypage.mapper

import com.moira.itda.domain.user.mypage.dto.response.MyPageResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyPageMapper {
    /**
     * 내 프로필 조회
     */
    fun selectMyPageResponse(userId: String): MyPageResponse?

    /**
     * 프로필 사진 변경 > 현재 프로필 사진의 URL 조회
     */
    fun selectCurrentFileUrl(userId: String): String?

    /**
     * 프로필 사진 변경 > 파일 ID 수정
     */
    fun updateFileId(userId: String, newFileId: String)

    /**
     * 닉네임 변경 > 닉네임 존재 여부 확인
     */
    fun selectNicknameChk(nickname: String): Boolean

    /**
     * 닉네임 변경
     */
    fun updateNickname(userId: String, newNickname: String)

    /**
     * 비밀번호 변경 > 현재 비밀번호 조회
     */
    fun selectCurrentPassword(userId: String): String

    /**
     * 비밀번호 변경
     */
    fun updatePassword(userId: String, newPassword: String)
}