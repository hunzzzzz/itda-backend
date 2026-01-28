package com.moira.itda.domain.gacha.common.mapper

import com.moira.itda.domain.gacha.common.dto.response.GachaItemNameResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaCommonMapper {
    /**
     * 가챠이력 저장 모달 > 가챠이력 저장 > 가챠 아이템목록 조회
     * 거래제안 모달 > 가챠 아이템목록 조회
     * 교환등록 > 가챠 아이템목록 조회
     * 판매등록 > 가챠 아이템목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<GachaItemNameResponse>
}