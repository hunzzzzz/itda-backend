package com.moira.itda.domain.gacha_pick.service

import com.moira.itda.domain.gacha_pick.dto.request.GachaPickRequest
import com.moira.itda.domain.gacha_pick.mapper.GachaPickMapper
import com.moira.itda.global.entity.GachaPickHistory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaPickService(
    private val mapper: GachaPickMapper
) {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 가챠 이력 저장
     */
    @Transactional
    fun pick(userId: String, gachaId: String, request: GachaPickRequest) {
        // [1] 해당 유저의 기존 GachaPickHistory 삭제
        mapper.deleteGachaPickHistory(userId = userId)

        // [2] GachaPickHistory 저장
        request.gachaItemIdList.forEach { gachaItemId ->
            val gachaPickHistory = GachaPickHistory.from(
                gachaId = gachaId,
                gachaItemId = gachaItemId,
                userId = userId
            )
            mapper.insertGachaPickHistory(gachaPickHistory = gachaPickHistory)
        }
    }
}