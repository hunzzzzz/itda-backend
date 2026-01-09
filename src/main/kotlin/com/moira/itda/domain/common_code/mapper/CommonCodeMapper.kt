package com.moira.itda.domain.common_code.mapper

import com.moira.itda.domain.common_code.dto.response.CodeDetailResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommonCodeMapper {
    /**
     * 공통 > 공통코드 조회
     */
    fun selectCodeDetailList(key: String): List<CodeDetailResponse>
}