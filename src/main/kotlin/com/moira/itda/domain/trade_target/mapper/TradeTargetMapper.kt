package com.moira.itda.domain.trade_target.mapper

import com.moira.itda.domain.trade_target.dto.response.TargetGachaItemResponse
import com.moira.itda.domain.trade_target.dto.response.TargetGachaResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeTargetMapper {
    /**
     * 거래대상 가챠지정 모달 > 즐겨찾기 목록 > totalElements 계산
     */
    fun selectGachaWishListCnt(userId: String): Long

    /**
     * 거래대상 가챠지정 모달 > 즐겨찾기 목록 > 가챠목록 조회
     */
    fun selectGachaWishList(userId: String, pageSize: Int, offset: Int): List<TargetGachaResponse>

    /**
     * 거래대상 가챠지정 모달 > 즐겨찾기 목록 > 가챠 아이템 목록 조회
     * 거래대상 가챠지정 모달 > 가챠목록 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<TargetGachaItemResponse>

    /**
     * 거래대상 가챠지정 모달 > 가챠목록 > totalElements 계산
     */
    fun selectGachaListCnt(keyword: String): Long

    /**
     * 거래대상 가챠지정 모달 > 가챠목록
     */
    fun selectGachaList(keyword: String, pageSize: Int, offset: Int): List<TargetGachaResponse>
}