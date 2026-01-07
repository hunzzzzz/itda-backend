package com.moira.itda.domain.user_feedback.service

import com.moira.itda.domain.user_feedback.dto.request.FeedbackRequest
import com.moira.itda.domain.user_feedback.dto.response.FeedbackPageResponse
import com.moira.itda.domain.user_feedback.mapper.UserFeedbackMapper
import com.moira.itda.global.entity.Feedback
import com.moira.itda.global.entity.FeedbackType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_FEEDBACK_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserFeedbackService(
    private val mapper: UserFeedbackMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 피드백
     */
    @Transactional
    fun feedback(userId: String, request: FeedbackRequest) {
        // [1] 유효성 검사 (type)
        runCatching { FeedbackType.valueOf(request.type) }
            .onFailure { throw ItdaException(ErrorCode.INVALID_FEEDBACK_TYPE) }

        // [2] 저장
        val feedback = Feedback.from(userId = userId, request = request)
        mapper.insertFeedback(feedback = feedback)
    }

    /**
     * 피드백 목록 조회
     */
    @Transactional(readOnly = true)
    fun getMyFeedbackList(userId: String, page: Int): FeedbackPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_FEEDBACK_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 피드백 목록 조회
        val totalElements = mapper.selectFeedbackListCnt(userId = userId)
        val contents = mapper.selectFeedbackList(userId = userId, pageSize = pageSize, offset = offset)

        // [3] 오프셋 페이지네이션 적용
        val page = pageHandler.getPageResponse(pageSize = pageSize, page = page, totalElements = totalElements)

        // [4] DTO 병합 후 리턴
        return FeedbackPageResponse(content = contents, page = page)
    }
}