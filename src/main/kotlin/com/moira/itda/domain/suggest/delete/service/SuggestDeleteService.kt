package com.moira.itda.domain.suggest.delete.service

import com.moira.itda.domain.suggest.common.component.SuggestValidator
import com.moira.itda.domain.suggest.common.mapper.SuggestCommonMapper
import com.moira.itda.domain.suggest.delete.mapper.SuggestDeleteMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestDeleteService(
    private val commonSuggestMapper: SuggestCommonMapper,
    private val mapper: SuggestDeleteMapper,
    private val validator: SuggestValidator
) {
    /**
     * 제안삭제 > 유효성 검사
     */
    private fun validateDeleteSuggest(userId: String, suggestUserId: String, suggestStatus: String) {
        // 권한
        validator.validateRole(userId = userId, suggestUserId = suggestUserId)

        // 상태값
        validator.validateStatusWhenDelete(suggestStatus = suggestStatus)
    }

    /**
     * 제안삭제
     */
    @Transactional
    fun deleteSuggest(userId: String, suggestId: String) {
        // [1] 제안 관련 정보 조회
        val infoMap = commonSuggestMapper.selectTradeSuggestInfo(suggestId = suggestId)
        val suggestStatus = infoMap["status"]
        val suggestUserId = infoMap["user_id"]

        if (suggestStatus != null && suggestUserId != null) {
            // [2] 유효성 검사
            this.validateDeleteSuggest(suggestStatus = suggestStatus, userId = userId, suggestUserId = suggestUserId)

            // [3] 제안취소
            mapper.updateTradeSuggestStatusDeleted(suggestId = suggestId)
        }
    }
}