package com.moira.itda.domain.admin.gacha.mapper

import com.moira.itda.domain.admin.gacha.dto.response.AdminGachaItemResponse
import com.moira.itda.domain.admin.gacha.dto.response.AdminGachaResponse
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaUpdateRequest
import com.moira.itda.global.entity.Gacha
import com.moira.itda.global.entity.GachaItem
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface AdminGachaMapper {
    /**
     * 어드민 페이지 > 가챠정보 > 등록 > 등록 파일 개수 확인
     */
    fun selectImageFileChk(fileId: String): Int

    /**
     * 어드민 페이지 > 가챠정보 > 등록 > Gacha 저장
     */
    fun insertGacha(gacha: Gacha)

    /**
     * 어드민 페이지 > 가챠정보 > 등록 > GachaItem 저장
     */
    fun insertGachaItem(gachaItem: GachaItem)

    /**
     * 어드민 페이지 > 가챠정보 > 전체 조회
     */
    fun selectGachaList(keywordPattern: String): List<AdminGachaResponse>

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<AdminGachaItemResponse>

    /**
     * 어드민 페이지 > 가챠정보 > 수정 > Gacha 수정
     */
    fun updateGacha(
        @Param("gachaId") gachaId: String,
        @Param("request") request: AdminGachaUpdateRequest
    )

    /**
     * 어드민 페이지 > 가챠정보 > 수정 > GachaItem 수정
     */
    fun updateGachaItem(itemId: Long, gachaId: String, newName: String)

    /**
     * 어드민 페이지 > 가챠정보 > 삭제 > 파일 ID 조회
     */
    fun selectFileId(gachaId: String): String?

    /**
     * 어드민 페이지 > 가챠정보 > 삭제 > 파일 URL 목록 조회
     */
    fun selectFileUrlList(fileId: String): List<String>

    /**
     * 어드민 페이지 > 가챠정보 > 삭제 > ImageFile 삭제
     */
    fun deleteImageFile(fileId: String)

    /**
     * 어드민 페이지 > 가챠정보 > 삭제 > GachaItem 전체 삭제
     */
    fun deleteGachaItemList(gachaId: String)

    /**
     * 어드민 페이지 > 가챠정보 > 삭제 > Gacha 삭제
     */
    fun deleteGacha(gachaId: String)

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 삭제 > GachaItem 삭제
     */
    fun deleteGachaItem(gachaId: String, itemId: Long)
}