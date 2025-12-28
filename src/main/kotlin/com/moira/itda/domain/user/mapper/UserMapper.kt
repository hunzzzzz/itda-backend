package com.moira.itda.domain.user.mapper

import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserLoginHistory
import com.moira.itda.global.entity.UserSignupIdentifyCode
import org.apache.ibatis.annotations.Mapper
import java.time.ZonedDateTime

@Mapper
interface UserMapper {
    /**
     * 회원가입 > 닉네임 중복 확인
     */
    fun selectNicknameChk(nickname: String): Int

    /**
     * 회원가입 > 이메일 중복 확인
     */
    fun selectEmailChk(email: String): Int

    /**
     * 회원가입 > 본인인증 > UserSignupIdentifyCode 저장
     */
    fun insertUserSignupIdentifyCode(userSignupIdentifyCode: UserSignupIdentifyCode)

    /**
     * 회원가입 > 본인인증 > UserSignupIdentifyCode 조회
     */
    fun selectUserSignupIdentifyCode(email: String): UserSignupIdentifyCode?
    
    /**
     * 회원가입 > User 저장
     */
    fun insertUser(user: User)

    // ---------------------------------------------------------------------------------------------- //

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

    // ---------------------------------------------------------------------------------------------- //

    /**
     * 로그아웃 > RefreshToken 초기화
     */
    fun updateRefreshTokenNull(userId: String)

    // ---------------------------------------------------------------------------------------------- //

    /**
     * 스케쥴러 (30분 간격) > UserSignupIdentifyCode 삭제
     */
    fun deleteUserSignupIdentifyCode()
}