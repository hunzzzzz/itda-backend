package com.moira.itda.domain.user.service

import com.moira.itda.domain.user_signup.component.IdentifyCodeGenerator
import com.moira.itda.domain.user.component.UserValidator
import com.moira.itda.domain.user.dto.request.NicknameUpdateRequest
import com.moira.itda.domain.user.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.user.dto.request.ProfileImageUpdateRequest
import com.moira.itda.domain.user.dto.request.ResetPasswordRequest
import com.moira.itda.domain.user.dto.response.MyPageResponse
import com.moira.itda.domain.user.mapper.UserMapper
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.entity.UserIdentifyCodeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import com.moira.itda.global.mail.component.UserMailSender
import com.moira.itda.global.mail.context.MailContext
import com.moira.itda.global.mail.context.MailContext.IDENTIFY_MAIL_TEXT
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class UserService(
    private val awsS3Handler: AwsS3Handler,
    private val cookieHandler: CookieHandler,
    private val encoder: PasswordEncoder,
    private val mailSender: UserMailSender,
    private val mapper: UserMapper,
    private val validator: UserValidator
) {
    /**
     * 로그아웃
     */
    @Transactional
    fun logout(userId: String, httpRes: HttpServletResponse) {
        // [1] RefreshToken 제거 (DB)
        mapper.updateRefreshTokenNull(userId = userId)

        // [2] RefreshToken 제거 (쿠키)
        cookieHandler.removeRtkInCookie(response = httpRes)
    }

    /**
     * 비밀번호 초기화 > 본인인증
     */
    @Transactional
    fun identifyForResetPassword(email: String) {
        // [1] 유효성 검사 (이메일 존재 여부 확인)
        validator.validateEmailExists(email = email)

        // [2] 6자리 인증번호 생성
        val code = IdentifyCodeGenerator.generate()

        // [3] 6자리 인증번호 저장 (TODO)
//        val userIdentifyCode = UserIdentifyCode.from(
//            email = email, code = code, type = UserIdentifyCodeType.RESET_PASSWORD
//        )
//        mapper.insertUserIdentifyCode(userIdentifyCode = userIdentifyCode)

        // [4] 이메일 전송
        mailSender.send(
            email = email,
            subject = MailContext.IDENTIFY_MAIL_SUBJECT,
            text = IDENTIFY_MAIL_TEXT.format(code)
        )
    }

    /**
     * 비밀번호 초기화 > 본인인증 > 코드 확인
     */
    fun checkIdentifyCodeForResetPassword(email: String, code: String) {
        // [1] UserSignupIdentifyCode 조회
        val userSignupIdentifyCode = mapper.selectUserIdentifyCode(
            email = email, type = UserIdentifyCodeType.RESET_PASSWORD
        )

        // [2] 코드가 존재하지 않거나, 만료된 경우
        if (userSignupIdentifyCode == null || userSignupIdentifyCode.expiredAt.isBefore(ZonedDateTime.now())) {
            throw ItdaException(ErrorCode.EXPIRED_IDENTIFY_CODE)
        }

        // [3] 코드가 일치하지 않는 경우
        if (code != userSignupIdentifyCode.code) {
            throw ItdaException(ErrorCode.INCORRECT_IDENTIFY_CODE)
        }
    }

    /**
     * 비밀번호 초기화
     */
    @Transactional
    fun resetPassword(request: ResetPasswordRequest) {
        // [1] 유효성 검사
        validator.validatePasswordRegex(request.newPassword)

        // [2] 비밀번호 변경
        mapper.updatePasswordByEmail(
            email = request.email,
            newPassword = encoder.encode(request.newPassword)
        )
    }

    /**
     * 마이페이지 > 내 프로필 조회
     */
    @Transactional(readOnly = true)
    fun getMyProfile(userId: String): MyPageResponse {
        // [1] 조회
        return mapper.selectMyPageResponse(userId = userId) ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)
    }

    /**
     * 마이페이지 > 프로필 사진 변경
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
     * 마이페이지 > 비밀번호 변경
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
        validator.validatePasswordUpdate(
            oldRaw = request.oldPassword,
            oldEncoded = currentPassword,
            new = request.newPassword
        )

        // [3] 비밀번호 변경
        mapper.updatePassword(userId = userId, newPassword = encoder.encode(request.newPassword))

        // [4] 로그아웃 메서드 호출
        this.logout(userId = userId, httpRes = httpRes)
    }

    /**
     * 마이페이지 > 회원탈퇴
     */
    @Transactional
    fun delete(userId: String, httpRes: HttpServletResponse) {
        // [1] 유효성 검사
        validator.validateDelete(userId = userId)

        // [2] 회원탈퇴
        mapper.updateUserStatusDeleted(userId = userId)

        // [3] 로그아웃 메서드 호출
        this.logout(userId = userId, httpRes = httpRes)
    }
}