package com.moira.itda.domain.common.identify.scheduler

import com.moira.itda.domain.common.identify.mapper.CommonIdentifyMapper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CommonIdentifyScheduler(
    private val mapper: CommonIdentifyMapper
) {
    /**
     * 30분 간격으로 만료된 본인인증코드를 삭제
     */
    @Scheduled(fixedRate = 1800000)
    fun delete() {
        mapper.deleteUserIdentifyCode()
    }
}