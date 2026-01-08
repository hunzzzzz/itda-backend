package com.moira.itda.domain.admin.feedback.service

import com.moira.itda.domain.admin.feedback.dto.request.FeedbackAnswerRequest
import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackDetailResponse
import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackResponse
import com.moira.itda.domain.admin.feedback.mapper.AdminUserFeedbackMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.mail.component.UserMailSender
import com.moira.itda.global.mail.context.MailContext.FEEDBACK_ANSWER_SUBJECT
import com.moira.itda.global.mail.context.MailContext.FEEDBACK_ANSWER_TEXT
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminUserFeedbackService(
    private val mailSender: UserMailSender,
    private val mapper: AdminUserFeedbackMapper
) {
    /**
     * 유저 피드백 목록 조회
     */
    @Transactional(readOnly = true)
    fun getFeedbackList(): List<AdminFeedbackResponse> {
        return mapper.selectFeedbackList()
    }

    /**
     * 유저 피드백 상세조회
     */
    @Transactional(readOnly = true)
    fun getFeedback(feedbackId: Long): AdminFeedbackDetailResponse {
        return mapper.selectFeedback(feedbackId = feedbackId)
            ?: throw ItdaException(ErrorCode.FEEDBACK_NOT_FOUND)
    }

    /**
     * 유저 피드백 답변
     */
    @Transactional
    fun answer(feedbackId: Long, request: FeedbackAnswerRequest) {
        // [1] Feedback 수정
        mapper.updateFeedbackAnswer(feedbackId = feedbackId, answerContent = request.content)

        // [2] 이메일 변수 세팅
        val email = mapper.selectUserEmail(feedbackId = feedbackId)
        val feedbackTypeKor = mapper.selectFeedbackTypeKor(feedbackId = feedbackId)

        // [3] 메일 전송
        if (email != null && feedbackTypeKor != null) {
            mailSender.send(
                email = email,
                subject = FEEDBACK_ANSWER_SUBJECT.format(feedbackTypeKor),
                text = FEEDBACK_ANSWER_TEXT.format(feedbackTypeKor, request.content)
            )
        }
        else {
            throw ItdaException(ErrorCode.MAIL_SYSTEM_ERROR)
        }
    }
}