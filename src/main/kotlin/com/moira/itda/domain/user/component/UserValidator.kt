package com.moira.itda.domain.user.component

import com.moira.itda.domain.user.mapper.UserMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val encoder: PasswordEncoder,
    private val mapper: UserMapper
) {
    private val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~])(?=.*\\d)?[A-Za-z\\d!@#$%^&*()\\-_+=\\[\\]{}|;:',.<>?/`~]{8,16}$")

    /**
     * 비밀번호 변경 > 유효성 검사
     */
    fun validatePasswordUpdate(oldRaw: String, oldEncoded: String, new: String) {
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
     * 회원탈퇴 > 유효성 검사
     */
    fun validateDelete(userId: String) {
        // [1] PENDING인 Trade가 존재하는지 확인
        if (mapper.selectPendingTradeChk(userId = userId)) {
            throw ItdaException(ErrorCode.PENDING_TRADE_EXISTS)
        }

        // [2] PENDING인 TradeSuggest가 존재하는지 확인
        if (mapper.selectPendingTradeSuggestChk(userId = userId)) {
            throw ItdaException(ErrorCode.PENDING_TRADE_SUGGEST_EXISTS)
        }

        // [3] ACTIVE인 ChatRoom이 존재하는지 확인
        if (mapper.selectActiveChatRoomChk(userId = userId)) {
            throw ItdaException(ErrorCode.ACTIVE_CHAT_ROOM_EXISTS)
        }
    }
}