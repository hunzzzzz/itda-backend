package com.moira.itda.domain.user_temp.mypage.service

import com.moira.itda.domain.user_temp.logout.service.LogoutService
import com.moira.itda.domain.user_temp.mypage.dto.request.NicknameUpdateRequest
import com.moira.itda.domain.user_temp.mypage.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.user_temp.mypage.dto.request.ProfileImageUpdateRequest
import com.moira.itda.domain.user_temp.mypage.dto.response.MyPageResponse
import com.moira.itda.domain.user_temp.mypage.mapper.MyPageMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyPageService(
    private val awsS3Handler: AwsS3Handler,
    private val logoutService: LogoutService,
    private val myPageMapper: MyPageMapper,
    private val passwordEncoder: PasswordEncoder
) {
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")

    /**
     * 마이페이지 > 내 프로필 조회
     */
    @Transactional(readOnly = true)
    fun getMyProfile(userId: String): MyPageResponse {
        return myPageMapper.selectMyPageResponse(userId = userId) ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)
    }

    /**
     * 마이페이지 > 프로필 사진 변경
     */
    @Transactional
    fun updateProfileImage(userId: String, request: ProfileImageUpdateRequest) {
        // [1] 기존 프로필 사진 URL 조회
        val currentImageUrl = myPageMapper.selectCurrentFileUrl(userId = userId)

        // [2] 기존 프로필 사진 삭제 (AWS S3)
        if (currentImageUrl != null) {
            awsS3Handler.delete(fileUrl = currentImageUrl)
        }

        // [3] 파일 ID 수정
        myPageMapper.updateFileId(userId = userId, newFileId = request.fileId)
    }

    /**
     * 마이페이지 > 닉네임 변경
     */
    @Transactional
    fun updateNickname(userId: String, request: NicknameUpdateRequest) {
        // [1] 유효성 검사
        if (myPageMapper.selectNicknameChk(nickname = request.newNickname) > 0) {
            throw ItdaException(ErrorCode.USING_NICKNAME)
        }

        // [2] 닉네임 변경
        myPageMapper.updateNickname(userId = userId, newNickname = request.newNickname)
    }

    /**
     * 마이페이지 > 비밀번호 변경
     */
    @Transactional
    fun updatePassword(
        userId: String,
        request: PasswordUpdateRequest,
        httpServletResponse: HttpServletResponse
    ) {
        // [1] 현재 비밀번호 조회
        val currentPassword = myPageMapper.selectCurrentPassword(userId = userId)

        // [2] 유효성 검사
        if (request.oldPassword.isBlank() || request.newPassword.isBlank() || !passwordRegex.matches(request.newPassword)) {
            throw ItdaException(ErrorCode.INVALID_PASSWORD)
        }
        if (!passwordEncoder.matches(request.oldPassword, currentPassword)) {
            throw ItdaException(ErrorCode.PASSWORD_NOT_MATCH)
        }
        if (request.oldPassword == request.newPassword) {
            throw ItdaException(ErrorCode.CANNOT_UPDATE_WITH_SAME_PASSWORD)
        }

        // [3] 비밀번호 변경
        myPageMapper.updatePassword(userId = userId, newPassword = passwordEncoder.encode(request.newPassword))

        // [4] 로그아웃 서비스 호출
        logoutService.logout(userId = userId, httpServletResponse = httpServletResponse)
    }
}