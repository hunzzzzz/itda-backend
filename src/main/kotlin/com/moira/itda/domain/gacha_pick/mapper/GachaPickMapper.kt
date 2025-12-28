package com.moira.itda.domain.gacha_pick.mapper

import com.moira.itda.global.entity.GachaPickHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaPickMapper {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 가챠 이력 저장 > 기존 GachaPickHistory 삭제
     */
    fun deleteGachaPickHistory(gachaId: String, userId: String)

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 가챠 이력 저장 > GachaPickHistory 저장
     */
    fun insertGachaPickHistory(gachaPickHistory: GachaPickHistory)
}