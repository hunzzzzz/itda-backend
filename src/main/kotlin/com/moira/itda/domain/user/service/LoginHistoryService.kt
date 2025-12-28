package com.moira.itda.domain.user.service

import com.moira.itda.domain.user.mapper.UserMapper
import com.moira.itda.global.entity.User
import com.moira.itda.global.entity.UserLoginHistory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class LoginHistoryService(
    private val mapper: UserMapper
) {
    /**
     * 로그인 > LoginHistory 저장 (실패)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun fail(user: User, ipAddress: String, userAgent: String) {
        val userLoginHistory = UserLoginHistory.from(
            userId = user.id,
            successYn = "N",
            ipAddress = ipAddress,
            userAgent = userAgent
        )

        mapper.insertUserLoginHistory(userLoginHistory = userLoginHistory)
    }

    /**
     * 로그인 > LoginHistory 저장 (성공)
     */
    @Transactional(propagation = Propagation.REQUIRED)
    fun success(user: User, ipAddress: String, userAgent: String) {
        val loginHistory = UserLoginHistory.from(
            userId = user.id,
            successYn = "Y",
            ipAddress = ipAddress,
            userAgent = userAgent
        )

        mapper.insertUserLoginHistory(userLoginHistory = loginHistory)
    }
}