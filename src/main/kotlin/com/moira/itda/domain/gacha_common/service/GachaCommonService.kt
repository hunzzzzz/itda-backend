package com.moira.itda.domain.gacha_common.service

import com.moira.itda.domain.gacha_common.dto.response.GachaItemNameResponse
import com.moira.itda.domain.gacha_common.mapper.GachaCommonMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaCommonService(
    private val mapper: GachaCommonMapper
) {
    /**
     * 가챠이력 저장 모달 > 가챠이력 저장 > 가챠 아이템목록 조회
     * 거래제안 모달 > 가챠 아이템목록 조회
     * 교환등록 > 가챠 아이템목록 조회
     * 판매등록 > 가챠 아이템목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaItemList(gachaId: String): List<GachaItemNameResponse> {
        return mapper.selectGachaItemNameList(gachaId = gachaId)
    }
}