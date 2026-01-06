package com.moira.itda.domain.user.mapper

import com.moira.itda.domain.user.dto.response.MyPageResponse
import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserLoginHistory
import com.moira.itda.global.entity.UserIdentifyCode
import com.moira.itda.global.entity.UserIdentifyCodeType
import org.apache.ibatis.annotations.Mapper
import java.time.ZonedDateTime

@Mapper
interface UserMapper {
    /**
     * 회원가입 > 닉네임 중복 확인
     * 마이페이지 > 닉네임 변경 > 닉네임 존재 여부 확인
     */
    fun selectNicknameChk(nickname: String): Boolean

    /**
     * 회원가입 > 이메일 중복 확인
     */
    fun selectEmailChk(email: String): Boolean

    /**
     * 회원가입 > 본인인증 > UserIdentifyCode 저장
     */
    fun insertUserIdentifyCode(userIdentifyCode: UserIdentifyCode)

    /**
     * 회원가입 > 본인인증 > UserIdentifyCode 조회
     */
    fun selectUserIdentifyCode(email: String, type: UserIdentifyCodeType): UserIdentifyCode?
    
    /**
     * 회원가입 > User 저장
     */
    fun insertUser(user: User)

    /**
     * 로그인 > User 조회
     * 토큰 재발급 > User 조회
     */
    fun selectUserByEmail(email: String): User?

    /**
     * 로그인 > 계정 정지 기한 조회
     */
    fun selectBannedUntil(userId: String): ZonedDateTime

    /**
     * 로그인 > RefreshToken 수정
     * 토큰 재발급 > RefreshToken 수정
     */
    fun updateRefreshToken(userId: String, rtk: String)

    /**
     * 로그인 > LoginHistory 저장
     */
    fun insertUserLoginHistory(userLoginHistory: UserLoginHistory)

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

    /**
     * 스케쥴러 (30분 간격) > UserIdentifyCode 삭제
     */
    fun deleteUserIdentifyCode()
}