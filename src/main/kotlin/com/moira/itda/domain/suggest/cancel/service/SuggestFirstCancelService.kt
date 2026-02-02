package com.moira.itda.domain.suggest.cancel.service

import com.moira.itda.domain.suggest.cancel.mapper.SuggestFirstCancelMapper
import com.moira.itda.domain.suggest.common.component.SuggestValidator
import com.moira.itda.domain.suggest.common.mapper.SuggestCommonMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestFirstCancelService(
    private val commonSuggestMapper: SuggestCommonMapper,
    private val mapper: SuggestFirstCancelMapper,
    private val validator: SuggestValidator
) {
    /**
     * 제안취소 (판매자 응답 전) > 유효성 검사
     */
    fun validateCancelSuggest(userId: String, suggestUserId: String, suggestStatus: String) {
        // 권한
        validator.validateRole(userId = userId, suggestUserId = suggestUserId)

        // 상태값
        validator.validateStatusWhenCancelBeforeResponse(suggestStatus = suggestStatus)
    }

    /**
     * 제안취소 (판매자 응답 전)
     */
    @Transactional
    fun cancelSuggest(userId: String, suggestId: String) {
        // [1] 제안 관련 정보 조회
        val infoMap = commonSuggestMapper.selectTradeSuggestInfo(suggestId = suggestId)
        val suggestStatus = infoMap["status"]
        val suggestUserId = infoMap["user_id"]

        if (suggestStatus != null && suggestUserId != null) {
            // [2] 유효성 검사
            this.validateCancelSuggest(userId = userId, suggestUserId = suggestUserId, suggestStatus = suggestStatus)

            // [3] 제안 취소
            mapper.updateTradeSuggestStatusCBR(suggestId = suggestId)
        } else {
            throw ItdaException(ErrorCode.FORBIDDEN)
        }
    }
}