package com.moira.itda.domain.gacha_add_suggest.mapper

import com.moira.itda.domain.gacha_add_suggest.dto.response.MyGachaAddSuggestResponse
import com.moira.itda.global.entity.GachaAddSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaAddSuggestMapper {
    /**
     * 정보등록요청 > GachaAddSuggest 저장
     */
    fun insertGachaAddSuggest(gachaAddSuggest: GachaAddSuggest)

    // ------------------------------------------------------------------------------------ //

    /**
     * 마이페이지 > 정보등록요청 결과 > 정보등록요청 목록 조회 > totalElements 계산
     */
    fun selectGachaAddSuggestListCnt(userId: String): Long

    /**
     * 마이페이지 > 정보등록요청 결과 > 정보등록요청 목록 조회
     */
    fun selectGachaAddSuggestList(userId: String, pageSize: Int, offset: Int): List<MyGachaAddSuggestResponse>
}