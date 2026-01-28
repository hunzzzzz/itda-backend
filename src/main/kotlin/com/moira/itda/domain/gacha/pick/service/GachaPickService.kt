package com.moira.itda.domain.gacha.pick.service

import com.moira.itda.domain.gacha.pick.dto.request.PickRequest
import com.moira.itda.domain.gacha.pick.mapper.GachaPickMapper
import com.moira.itda.global.entity.GachaPickHistory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaPickService(
    private val mapper: GachaPickMapper
) {
    /**
     * 가챠이력 저장
     */
    @Transactional
    fun pick(userId: String, gachaId: String, request: PickRequest) {
        // [1] 해당 유저의 기존 GachaPickHistory 삭제
        mapper.deleteGachaPickHistory(gachaId = gachaId, userId = userId)

        // [2] GachaPickHistory 저장
        request.gachaItems.forEach { (gachaItemId, count) ->
            repeat(count) {
                val gachaPickHistory = GachaPickHistory.from(
                    gachaId = gachaId,
                    gachaItemId = gachaItemId,
                    userId = userId
                )
                mapper.insertGachaPickHistory(gachaPickHistory = gachaPickHistory)
            }
        }
    }
}