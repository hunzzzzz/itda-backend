package com.moira.itda.domain.gacha.component

import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class GachaValidator {
    private val sortList = listOf<String>(
        "LATEST",      // 최신순
        "OLDEST",      // 오래된순
        "PRICE_ASC",   // 낮은 가격순
        "PRICE_DESC",  // 높은 가격순
        "MOST_WISHED", // 높은 즐겨찾기순
        "MOST_TRADED"  // TODO: 높은 거래량순
    )

    /**
     * 가챠정보 > 가챠목록 > 유효성 검사 (정렬 조건)
     */
    fun validateSort(sort: String) {
        if (!sortList.contains(sort.uppercase())) {
            throw ItdaException(ErrorCode.INVALID_SORT_CONDITION)
        }
    }
}