package com.moira.itda.domain.gacha_wish.mapper

import com.moira.itda.global.entity.GachaWish
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaWishMapper {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 즐겨찾기 여부 조회
     */
    fun selectGachaWishChk(userId: String, gachaId: String): Boolean

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 즐겨찾기 > GachaWish 저장
     */
    fun insertGachaWish(gachaWish: GachaWish)

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 즐겨찾기 > GachaWish 삭제
     */
    fun deleteGachaWish(userId: String, gachaId: String)
}