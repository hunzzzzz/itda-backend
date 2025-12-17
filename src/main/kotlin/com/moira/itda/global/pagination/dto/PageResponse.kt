package com.moira.itda.global.pagination.dto

data class PageResponse(
    val size: Int,
    val number: Int,
    val totalElements: Long,
    val totalPages: Long
)