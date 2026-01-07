package com.moira.itda.domain.user_feedback.service

import com.moira.itda.domain.common.mapper.CommonMapper
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
    private val commonMapper: CommonMapper,
    private val mapper: UserFeedbackMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    private fun validateFeedback(request: FeedbackRequest) {
        // 타입
        runCatching { FeedbackType.valueOf(request.type) }
            .onFailure { throw ItdaException(ErrorCode.INVALID_FEEDBACK_TYPE) }
        // 파일
        if (request.fileId != null) {
            if (!commonMapper.selectFileIdChk(fileId = request.fileId)) {
                throw ItdaException(ErrorCode.FILE_NOT_FOUND)
            }
        }
        // 내용
        if (request.content.isBlank()) {
            throw ItdaException(ErrorCode.NO_FEEDBACK_CONTENT)
        }
        // 이메일 수신 여부
        if (request.receiveEmailYn.isBlank() || (request.receiveEmailYn != "Y" && request.receiveEmailYn != "N")) {
            throw ItdaException(ErrorCode.INVALID_RECEIVE_EMAIL_YN)
        }
    }

    /**
     * 피드백
     */
    @Transactional
    fun feedback(userId: String, request: FeedbackRequest) {
        // [1] 유효성 검사
        this.validateFeedback(request = request)

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

        // [3] 이미지 파일 목록 조회
        contents.forEach {
            if (it.fileId != null) {
                it.fileUrls = commonMapper.selectImageFileUrl(fileId = it.fileId).map { it.fileUrl }
            }
        }

        // [4] 오프셋 페이지네이션 적용
        val page = pageHandler.getPageResponse(pageSize = pageSize, page = page, totalElements = totalElements)

        // [5] DTO 병합 후 리턴
        return FeedbackPageResponse(content = contents, page = page)
    }
}