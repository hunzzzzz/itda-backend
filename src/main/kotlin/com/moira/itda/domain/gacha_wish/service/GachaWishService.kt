package com.moira.itda.domain.gacha_wish.service

import com.moira.itda.domain.gacha_wish.mapper.GachaWishMapper
import com.moira.itda.global.entity.GachaWish
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class GachaWishService(
    private val mapper: GachaWishMapper
) {
    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 즐겨찾기
     */
    @Transactional
    fun wish(userId: String, gachaId: String) {
        // [1] 즐겨찾기 여부 조회
        val wishYn = if (mapper.selectGachaWishChk(userId = userId, gachaId = gachaId)) "Y" else "N"

        // [2-1] GachaWish 저장
        if ("N" == wishYn) {
            val gachaWish = GachaWish(id = null, userId = userId, gachaId = gachaId, createdAt = ZonedDateTime.now())
            mapper.insertGachaWish(gachaWish = gachaWish)
        }
        // [2-2] GachaWish 삭제
        else {
            mapper.deleteGachaWish(userId = userId, gachaId = gachaId)
        }
    }
}