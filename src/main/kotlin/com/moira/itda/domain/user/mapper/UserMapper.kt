package com.moira.itda.domain.user.mapper

import com.moira.itda.domain.user.dto.response.MyPageResponse
import com.moira.itda.global.entity.UserIdentifyCode
import com.moira.itda.global.entity.UserIdentifyCodeType
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    /**
     * 회원가입 > 이메일 중복 확인
     */
    fun selectEmailChk(email: String): Boolean

    /**
     * 회원가입 > 본인인증 > UserIdentifyCode 조회
     */
    fun selectUserIdentifyCode(email: String, type: UserIdentifyCodeType): UserIdentifyCode?

    /**
     * 로그아웃 > RefreshToken 초기화
     */
    fun updateRefreshTokenNull(userId: String)

    /**
     * 비밀번호 초기화
     */
    fun updatePasswordByEmail(email: String, newPassword: String)

    /**
     * 마이페이지 > 내 프로필 조회
     */
    fun selectMyPageResponse(userId: String): MyPageResponse?

    /**
     * 마이페이지 > 프로필 사진 변경 > 현재 프로필 사진의 URL 조회
     */
    fun selectCurrentFileUrl(userId: String): String?

    /**
     * 마이페이지 > 프로필 사진 변경 > 파일 ID 수정
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
     * 마이페이지 > 비밀번호 변경 > 현재 비밀번호 조회
     */
    fun selectCurrentPassword(userId: String): String

    /**
     * 마이페이지 > 비밀번호 변경
     */
    fun updatePassword(userId: String, newPassword: String)

    /**
     * 마이페이지 > 회원탈퇴 > User status 변경 (DELETED)
     */
    fun updateUserStatusDeleted(userId: String)

    /**
     * 마이페이지 > 회윈털퇴 > PENDING인 Trade 존재 여부 확인
     */
    fun selectPendingTradeChk(userId: String): Boolean

    /**
     * 마이페이지 > 회원탈퇴 > PENDING인 TradeSuggest 존재 여부 확인
     */
    fun selectPendingTradeSuggestChk(userId: String): Boolean

    /**
     * 마이페이지 > 회원탈퇴 > ACTIVE인 ChatRoom 존재 여부 확인
     */
    fun selectActiveChatRoomChk(userId: String): Boolean

    /**
     * 스케쥴러 (30분 간격) > UserIdentifyCode 삭제
     */
    fun deleteUserIdentifyCode()
}