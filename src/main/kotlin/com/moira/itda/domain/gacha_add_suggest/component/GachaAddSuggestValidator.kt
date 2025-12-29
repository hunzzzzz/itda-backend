package com.moira.itda.domain.gacha_add_suggest.component

import com.moira.itda.domain.common.mapper.CommonMapper
import com.moira.itda.domain.gacha_add_suggest.dto.request.GachaAddSuggestRequest
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class GachaAddSuggestValidator(
    private val commonMapper: CommonMapper
) {
    /**
     * 정보등록요청 > 유효성 검사
     */
    fun validate(request: GachaAddSuggestRequest) {
        // 파일 ID
        if (request.fileId != null) {
            if (!commonMapper.selectFileIdChk(fileId = request.fileId)) {
                throw ItdaException(ErrorCode.FILE_NOT_FOUND)
            }
        }
        // 내용
        if (request.content.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_GACHA_ADD_SUGGEST_CONTENT)
        }
        // 이메일 수신 여부
        if (request.receiveEmailYn.isBlank() || (request.receiveEmailYn != "Y" && request.receiveEmailYn != "N")) {
            throw ItdaException(ErrorCode.INVALID_GACHA_ADD_SUGGEST_MESSAGE_YN)
        }
    }
}