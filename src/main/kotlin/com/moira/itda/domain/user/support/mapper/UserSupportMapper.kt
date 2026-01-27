package com.moira.itda.domain.user.support.mapper

import com.moira.itda.domain.user.support.dto.response.SupportResponse
import com.moira.itda.global.entity.UserSupport
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserSupportMapper {
    /**
     * 문의 등록 > UserSupport 저장
     */
    fun insertUserSupport(userSupport: UserSupport)

    /**
     * 문의 목록 조회 > totalElements 계산
     */
    fun selectUserSupportListCnt(userId: String): Long

    /**
     * 문의 목록 조회
     */
    fun selectUserSupportList(userId: String, pageSize: Int, offset: Int): List<SupportResponse>
}