package com.moira.itda.domain.user_activity_suggest.service

import com.moira.itda.domain.user_activity_suggest.dto.response.MyTradeSuggestPageResponse
import com.moira.itda.domain.user_activity_suggest.mapper.UserActivitySuggestMapper
import com.moira.itda.global.entity.TradeSuggestStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_SUGGEST_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserActivitySuggestService(
    private val mapper: UserActivitySuggestMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * [내부 메서드] 유저의 권한 검증
     */
    private fun validateUserId(userId: String, suggestUserId: String) {
        if (userId != suggestUserId) {
            throw ItdaException(ErrorCode.OTHERS_SUGGEST)
        }
    }

    /**
     * [내부 메서드] 제안취소 시 유효성 검사
     */
    private fun validateCancelSuggest(suggestStatus: String, userId: String, suggestUserId: String) {
        // [1] status에 대한 유효성 검사
        when (suggestStatus) {
            TradeSuggestStatus.APPROVED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_APPROVED_SUGGEST)
            }

            TradeSuggestStatus.REJECTED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_REJECTED_SUGGEST)
            }

            TradeSuggestStatus.CANCELED_BEFORE_RESPONSE.name,
            TradeSuggestStatus.CANCELED.name -> {
                throw ItdaException(ErrorCode.ALREADY_CANCELED_SUGGEST)
            }

            TradeSuggestStatus.DELETED.name -> {
                throw ItdaException(ErrorCode.ALREADY_DELETED_SUGGEST)
            }
        }

        // [2] 권한에 대한 유효성 검사
        this.validateUserId(userId = userId, suggestUserId = suggestUserId)
    }

    /**
     * [내부 메서드] 제안삭제 시 유효성 검사
     */
    private fun validateDeleteSuggest(suggestStatus: String, userId: String, suggestUserId: String) {
        // [1] status에 대한 유효성 검사
        when (suggestStatus) {
            TradeSuggestStatus.APPROVED.name -> {
                throw ItdaException(ErrorCode.CANNOT_DELETE_APPROVED_SUGGEST)
            }

            TradeSuggestStatus.PENDING.name -> {
                throw ItdaException(ErrorCode.CANNOT_DELETE_PENDING_SUGGEST)
            }

            TradeSuggestStatus.DELETED.name -> {
                throw ItdaException(ErrorCode.ALREADY_DELETED_SUGGEST)
            }
        }

        // [2] 권한에 대한 유효성 검사
        this.validateUserId(userId = userId, suggestUserId = suggestUserId)
    }

    /**
     * 내 활동 > 제안 > 내 제안목록 조회
     */
    @Transactional(readOnly = true)
    fun getMySuggestList(userId: String, page: Int): MyTradeSuggestPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_TRADE_SUGGEST_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 거래 정보 조회
        val totalElements = mapper.selectMyTradeSuggestListCnt(userId = userId)
        val content = mapper.selectMyTradeSuggestList(
            userId = userId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = pageHandler.getPageResponse(
            page = page,
            pageSize = pageSize,
            totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return MyTradeSuggestPageResponse(content = content, page = pageResponse)
    }

    /**
     * 내 활동 > 제안 > 제안취소
     */
    @Transactional
    fun cancelSuggest(userId: String, suggestId: String) {
        // [1] 제안 관련 정보 조회
        val infoMap = mapper.selectTradeSuggestInfo(suggestId = suggestId)
        val suggestStatus = infoMap["status"] ?: throw ItdaException(ErrorCode.SUGGEST_NOT_FOUND)
        val suggestUserId = infoMap["user_id"] ?: throw ItdaException(ErrorCode.SUGGEST_NOT_FOUND)

        // [2] 유효성 검사
        this.validateCancelSuggest(suggestStatus = suggestStatus, userId = userId, suggestUserId = suggestUserId)

        // [3] 제안취소
        mapper.updateTradeSuggestStatusCBR(suggestId = suggestId)
    }

    /**
     * 내 활동 > 제안 > 제안삭제
     */
    @Transactional
    fun deleteSuggest(userId: String, suggestId: String) {
        // [1] 제안 관련 정보 조회
        val infoMap = mapper.selectTradeSuggestInfo(suggestId = suggestId)
        val suggestStatus = infoMap["status"] ?: throw ItdaException(ErrorCode.SUGGEST_NOT_FOUND)
        val suggestUserId = infoMap["user_id"] ?: throw ItdaException(ErrorCode.SUGGEST_NOT_FOUND)

        // [2] 유효성 검사
        this.validateDeleteSuggest(suggestStatus = suggestStatus, userId = userId, suggestUserId = suggestUserId)

        // [3] 제안취소
        mapper.updateTradeSuggestStatusDeleted(suggestId = suggestId)
    }
}