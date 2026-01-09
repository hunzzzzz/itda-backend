package com.moira.itda.domain.common_code.service

import com.moira.itda.domain.common_code.dto.response.AllCodeDetailResponse
import com.moira.itda.domain.common_code.dto.response.CodeDetailResponse
import com.moira.itda.domain.common_code.mapper.CommonCodeMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommonCodeService(
    private val mapper: CommonCodeMapper
) {
    /**
     * 공통 > 공통코드 조회
     */
    @Transactional(readOnly = true)
    fun getCode(key: String): List<CodeDetailResponse> {
        return mapper.selectCodeDetailList(key = key)
    }

    /**
     * 공통 > 공통코드 일괄 조회
     */
    @Transactional(readOnly = true)
    fun getAllCode(keyList: List<String?>): AllCodeDetailResponse {
        val response = AllCodeDetailResponse(null, null, null, null, null)

        keyList.forEachIndexed { index, key ->
            if (key != null) {
                when (index) {
                    0 -> response.code1 = mapper.selectCodeDetailList(key = key)
                    1 -> response.code2 = mapper.selectCodeDetailList(key = key)
                    2 -> response.code3 = mapper.selectCodeDetailList(key = key)
                    3 -> response.code4 = mapper.selectCodeDetailList(key = key)
                    4 -> response.code5 = mapper.selectCodeDetailList(key = key)
                }
            }
        }

        return response
    }
}