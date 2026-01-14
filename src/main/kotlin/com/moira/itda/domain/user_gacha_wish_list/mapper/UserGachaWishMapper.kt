package com.moira.itda.domain.user_gacha_wish_list.mapper

import com.moira.itda.domain.user_gacha_wish_list.dto.response.WishGachaResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserGachaWishMapper {
    /**
     * 즐겨찾기 가챠목록 조회 > totalElements 계산
     */
    fun selectWishGachaListCnt(userId: String): Long

    /**
     * 즐겨찾기 가챠목록 조회
     */
    fun selectWishGachaList(userId: String, pageSize: Int, offset: Int): List<WishGachaResponse>
}