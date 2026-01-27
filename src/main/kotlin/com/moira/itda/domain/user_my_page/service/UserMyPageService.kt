package com.moira.itda.domain.user_my_page.service

import com.moira.itda.domain.account.logout.service.LogoutService
import com.moira.itda.domain.user_my_page.dto.request.NicknameUpdateRequest
import com.moira.itda.domain.user_my_page.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.user_my_page.dto.request.ProfileImageUpdateRequest
import com.moira.itda.domain.user_my_page.dto.response.MyPageResponse
import com.moira.itda.domain.user_my_page.mapper.UserMyPageMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserMyPageService(
    private val awsS3Handler: AwsS3Handler,
    private val encoder: PasswordEncoder,
    private val logoutService: LogoutService,
    private val mapper: UserMyPageMapper
) {
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")

    /**
     * 내 프로필 조회
     */
    @Transactional(readOnly = true)
    fun getMyProfile(userId: String): MyPageResponse {
        return mapper.selectMyPageResponse(userId = userId) ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)
    }

    /**
     * 프로필 사진 변경
     */
    @Transactional
    fun updateProfileImage(userId: String, request: ProfileImageUpdateRequest) {
        // [1] 기존 프로필 사진 URL 조회
        val currentImageUrl = mapper.selectCurrentFileUrl(userId = userId)

        // [2] 기존 프로필 사진 삭제 (AWS S3)
        if (currentImageUrl != null) {
            awsS3Handler.delete(fileUrl = currentImageUrl)
        }

        // [3] 파일 ID 수정
        mapper.updateFileId(userId = userId, newFileId = request.fileId)
    }

    /**
     * 닉네임 변경
     */
    @Transactional
    fun updateNickname(userId: String, request: NicknameUpdateRequest) {
        // [1] 유효성 검사
        if (mapper.selectNicknameChk(nickname = request.newNickname)) {
            throw ItdaException(ErrorCode.USING_NICKNAME)
        }

        // [2] 닉네임 변경
        mapper.updateNickname(userId = userId, newNickname = request.newNickname)
    }

    /**
     * 비밀번호 변경 > 유효성 검사
     */
    private fun validatePasswordUpdate(oldRaw: String, oldEncoded: String, new: String) {
        // 비밀번호 입력값 검증
        if (oldRaw.isBlank() || new.isBlank() || !passwordRegex.matches(new)) {
            throw ItdaException(ErrorCode.INVALID_PASSWORD)
        }
        // 기존 비밀번호 일치 여부 검증
        if (!encoder.matches(oldRaw, oldEncoded)) {
            throw ItdaException(ErrorCode.PASSWORD_NOT_MATCH)
        }
        // 기존 비밀번호와 새로운 비밀번호가 다른지 여부 검증
        if (oldRaw == new) {
            throw ItdaException(ErrorCode.SAME_PASSWORD)
        }
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    fun updatePassword(
        userId: String,
        request: PasswordUpdateRequest,
        httpRes: HttpServletResponse
    ) {
        // [1] 현재 비밀번호 조회
        val currentPassword = mapper.selectCurrentPassword(userId = userId)

        // [2] 유효성 검사
        this.validatePasswordUpdate(
            oldRaw = request.oldPassword,
            oldEncoded = currentPassword,
            new = request.newPassword
        )

        // [3] 비밀번호 변경
        mapper.updatePassword(userId = userId, newPassword = encoder.encode(request.newPassword))

        // [4] 로그아웃 메서드 호출
        logoutService.logout(userId = userId, httpRes = httpRes)
    }
}