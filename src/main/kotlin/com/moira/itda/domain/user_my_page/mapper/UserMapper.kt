package com.moira.itda.domain.user_my_page.mapper

import com.moira.itda.domain.user_my_page.dto.response.MyPageResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    /**
     * 로그아웃 > RefreshToken 초기화
     */
    fun updateRefreshTokenNull(userId: String)

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

    /**
     * 회원탈퇴 > User status 변경 (DELETED)
     */
    fun updateUserStatusDeleted(userId: String)

    /**
     * 회윈털퇴 > PENDING인 Trade 존재 여부 확인
     */
    fun selectPendingTradeChk(userId: String): Boolean

    /**
     * 회원탈퇴 > PENDING인 TradeSuggest 존재 여부 확인
     */
    fun selectPendingTradeSuggestChk(userId: String): Boolean

    /**
     * 회원탈퇴 > ACTIVE인 ChatRoom 존재 여부 확인
     */
    fun selectActiveChatRoomChk(userId: String): Boolean
}