package com.moira.itda.domain.admin.code.mapper

import com.moira.itda.domain.admin.code.dto.request.AdminCodeDetailUpdateRequest
import com.moira.itda.domain.admin.code.dto.response.AdminCodeDetailResponse
import com.moira.itda.domain.admin.code.dto.response.AdminCodeResponse
import com.moira.itda.global.entity.CommonCode
import com.moira.itda.global.entity.CommonCodeDetail
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface AdminCodeMapper {
    /**
     * 어드민 페이지 > 공통코드 > 등록 > codeKey 존재 여부 확인
     */
    fun selectCodeKeyChk(codeKey: String): Int

    /**
     * 어드민 페이지 > 공통코드 > 등록 > CommonCode 저장
     */
    fun insertCommonCode(commonCode: CommonCode)

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 등록 > key, engName 존재 여부 확인
     */
    fun selectKeyAndEngNameChk(key: String, engName: String): Boolean

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 등록 > CommonCodeDetail 저장
     */
    fun insertCommonCodeDetail(commonCodeDetail: CommonCodeDetail)

    /**
     * 어드민 페이지 > 공통코드 > 전체 조회
     */
    fun selectCommonCodeList(): List<AdminCodeResponse>

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 목록 조회
     */
    fun selectCommonCodeDetailList(key: String): List<AdminCodeDetailResponse>

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 수정 > engName 존재 여부 확인
     */
    fun selectEngNameChk2(detailId: Long, engName: String): Int

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 수정 > CommonCodeDetail 수정
     */
    fun updateCommonCodeDetail(
        @Param("request") request: AdminCodeDetailUpdateRequest,
        @Param("key") key: String,
        @Param("detailId") detailId: Long
    )

    /**
     * 어드민 페이지 > 공통코드 > CommonCode 삭제
     */
    fun deleteCommonCode(key: String)

    /**
     * 어드민 페이지 > 공통코드 > CommonCodeDetail 전체 삭제
     */
    fun deleteCommonCodeDetailList(key: String)

    /**
     * 어드민 페이지 > 공통코드 > CommonCodeDetail 삭제
     */
    fun deleteCommonCodeDetail(key: String, detailId: Long)
}