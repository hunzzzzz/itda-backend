package com.moira.itda.domain.admin.info.mapper

import com.moira.itda.domain.admin.info.dto.response.AdminGachaInfoAddDetailResponse
import com.moira.itda.domain.admin.info.dto.response.AdminGachaInfoAddResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdminGachaInfoAddMapper {
    /**
     * 어드민 페이지 > 정보등록요청 탭 > 정보등록요청 목록 조회
     */
    fun selectGachaInfoAddList(): List<AdminGachaInfoAddResponse>

    /**
     * 어드민 페이지 > 정보등록요청 탭 > 정보등록요청 목록 조회 > 정보등록요청 상세 정보 조회
     */
    fun selectGachaInfoAdd(infoId: String): AdminGachaInfoAddDetailResponse
}