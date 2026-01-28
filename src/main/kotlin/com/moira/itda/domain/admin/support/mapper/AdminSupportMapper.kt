package com.moira.itda.domain.admin.support.mapper

import com.moira.itda.domain.admin.support.dto.response.SupportResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdminSupportMapper {
    /**
     * 문의 목록 조회
     */
    fun selectUserSupportList(): List<SupportResponse>

    /**
     * 문의 답변 > UserSupport 수정
     */
    fun updateUserSupportAnswerContent(supportId: Long, answerContent: String)
}