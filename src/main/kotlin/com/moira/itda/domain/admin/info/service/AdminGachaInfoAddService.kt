package com.moira.itda.domain.admin.info.service

import com.moira.itda.domain.admin.info.dto.response.AdminGachaInfoAddResponse
import com.moira.itda.domain.admin.info.mapper.AdminGachaInfoAddMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminGachaInfoAddService(
    private val adminGachaInfoAddMapper: AdminGachaInfoAddMapper
) {
    /**
     * 어드민 페이지 > 정보등록요청 탭 > 정보등록요청 목록 조회
     */
    @Transactional(readOnly = true)
    fun getAddInfos(): List<AdminGachaInfoAddResponse> {
        return adminGachaInfoAddMapper.selectGachaInfoAddList()
    }

}