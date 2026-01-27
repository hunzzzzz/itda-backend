package com.moira.itda.domain.common.identify.dto.request

import com.moira.itda.global.entity.UserIdentifyCodeType

data class IdentifyCheckRequest(
    val phoneNumber: String,
    val type: UserIdentifyCodeType,
    val code: String
)