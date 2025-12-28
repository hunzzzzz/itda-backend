package com.moira.itda.domain.user_temp.mypage.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyPageMapper {
    /**
     * 마이페이지 > 프로필 사진 변경 > 현재 프로필 사진 Url 조회
     */
    fun selectCurrentFileUrl(userId: String): String?

    /**
     * 마이페이지 > 프로피 사진 변경 > 파일 ID 수정
     */
    fun updateFileId(userId: String, newFileId: String)

    /**
     * 마이페이지 > 닉네임 변경 > 닉네임 존재 여부 확인
     */
    fun selectNicknameChk(nickname: String): Int

    /**
     * 마이페이지 > 닉네임 변경
     */
    fun updateNickname(userId: String, newNickname: String)

    /**
     * 마이페이지 > 비밀번호 변경 > 현재 비밀번호 조회
     */
    fun selectCurrentPassword(userId: String): String

    /**
     * 마이페이지 > 비밀번호 변경
     */
    fun updatePassword(userId: String, newPassword: String)
}