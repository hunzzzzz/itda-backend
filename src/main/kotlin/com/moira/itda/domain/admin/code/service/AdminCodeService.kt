package com.moira.itda.domain.admin.code.service

import com.moira.itda.domain.admin.code.dto.request.AdminCodeAddRequest
import com.moira.itda.domain.admin.code.dto.request.AdminCodeDetailAddRequest
import com.moira.itda.domain.admin.code.dto.request.AdminCodeDetailUpdateRequest
import com.moira.itda.domain.admin.code.dto.response.AdminCodeDetailResponse
import com.moira.itda.domain.admin.code.dto.response.AdminCodeResponse
import com.moira.itda.domain.admin.code.mapper.AdminCodeMapper
import com.moira.itda.global.entity.CommonCode
import com.moira.itda.global.entity.CommonCodeDetail
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminCodeService(
    private val adminCodeMapper: AdminCodeMapper
) {
    /**
     * 어드민 페이지 > 공통코드 > 등록
     */
    @Transactional
    fun add(request: AdminCodeAddRequest) {
        // [1] 유효성 검사
        if (request.key.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_CODE_KEY)
        }
        if (adminCodeMapper.selectCodeKeyChk(codeKey = request.key) > 0) {
            throw ItdaException(ErrorCode.ALREADY_USING_KEY)
        }

        // [2] CommonCode 저장
        val commonCode = CommonCode.fromCommonCodeAddRequest(request = request)
        adminCodeMapper.insertCommonCode(commonCode = commonCode)
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 등록
     */
    @Transactional
    fun addDetail(key: String, request: AdminCodeDetailAddRequest) {
        // [1] 유효성 검사
        if (adminCodeMapper.selectEngNameChk(engName = request.engName) > 0) {
            throw ItdaException(ErrorCode.ALREADY_USING_CODE_DETAIL_NAME)
        }

        // [2] 저장
        val commonCodeDetail = CommonCodeDetail.fromCommonCodeDetailAddRequest(
            codeKey = key,
            request = request
        )
        adminCodeMapper.insertCommonCodeDetail(commonCodeDetail = commonCodeDetail)
    }

    /**
     * 어드민 페이지 > 공통코드 > 전체 조회
     */
    @Transactional(readOnly = true)
    fun getAll(): List<AdminCodeResponse> {
        return adminCodeMapper.selectCommonCodeList()
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 목록 조회
     */
    @Transactional(readOnly = true)
    fun getDetails(key: String): List<AdminCodeDetailResponse> {
        return adminCodeMapper.selectCommonCodeDetailList(key = key)
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 수정
     */
    @Transactional
    fun updateDetail(key: String, detailId: Long, request: AdminCodeDetailUpdateRequest) {
        // [1] 유효성 검사
        if (adminCodeMapper.selectEngNameChk2(detailId = detailId, engName = request.engName) > 0) {
            throw ItdaException(ErrorCode.ALREADY_USING_CODE_DETAIL_NAME)
        }

        // [2] 수정
        adminCodeMapper.updateCommonCodeDetail(request = request, key = key, detailId = detailId)
    }

    /**
     * 어드민 페이지 > 공통코드 > 삭제
     */
    @Transactional
    fun delete(key: String) {
        adminCodeMapper.deleteCommonCodeDetailList(key = key)
        adminCodeMapper.deleteCommonCode(key = key)
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 삭제
     */
    @Transactional
    fun deleteDetail(key: String, detailId: Long) {
        adminCodeMapper.deleteCommonCodeDetail(key = key, detailId = detailId)
    }
}