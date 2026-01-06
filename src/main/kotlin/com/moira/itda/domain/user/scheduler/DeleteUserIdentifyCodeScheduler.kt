package com.moira.itda.domain.user.scheduler

import com.moira.itda.domain.user.mapper.UserMapper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DeleteUserIdentifyCodeScheduler(
    private val mapper: UserMapper
) {
    /**
     * 30분 간격으로 만료된 본인인증코드를 삭제
     */
    @Scheduled(fixedRate = 1800000)
    fun delete() {
        mapper.deleteUserIdentifyCode()
    }
}