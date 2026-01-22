package com.moira.itda.domain.user_identify.dto.request

import com.moira.itda.global.entity.UserIdentifyCodeType

data class IdentifyCheckRequest(
    val phoneNumber: String,
    val type: UserIdentifyCodeType,
    val code: String
)