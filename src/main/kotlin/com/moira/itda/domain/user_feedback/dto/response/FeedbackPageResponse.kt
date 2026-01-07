package com.moira.itda.domain.user_feedback.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class FeedbackPageResponse(
    val content: List<FeedbackResponse>,
    val page: PageResponse
)
