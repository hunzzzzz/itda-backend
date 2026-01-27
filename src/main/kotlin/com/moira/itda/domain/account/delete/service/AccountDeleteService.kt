package com.moira.itda.domain.account.delete.service

import com.moira.itda.domain.account.delete.mapper.AccountDeleteMapper
import com.moira.itda.domain.account.logout.service.LogoutService
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountDeleteService(
    private val logoutService: LogoutService,
    private val mapper: AccountDeleteMapper
) {
    /**
     * 회원탈퇴 > 유효성 검사
     */
    private fun validateDelete(userId: String) {
        // PENDING인 Trade가 존재하는지 확인
        if (mapper.selectPendingTradeChk(userId = userId)) {
            throw ItdaException(ErrorCode.PENDING_TRADE_EXISTS)
        }

        // PENDING인 TradeSuggest가 존재하는지 확인
        if (mapper.selectPendingTradeSuggestChk(userId = userId)) {
            throw ItdaException(ErrorCode.PENDING_TRADE_SUGGEST_EXISTS)
        }

        // ACTIVE인 ChatRoom이 존재하는지 확인
        if (mapper.selectActiveChatRoomChk(userId = userId)) {
            throw ItdaException(ErrorCode.ACTIVE_CHAT_ROOM_EXISTS)
        }
    }

    /**
     * 회원탈퇴
     */
    @Transactional
    fun delete(userId: String, httpRes: HttpServletResponse) {
        // [1] 유효성 검사
        this.validateDelete(userId = userId)

        // [2] 회원탈퇴
        mapper.updateUserStatusDeleted(userId = userId)

        // [3] 로그아웃 메서드 호출
        logoutService.logout(userId = userId, httpRes = httpRes)
    }
}