package com.moira.itda.domain.gacha.list.component

enum class GachaListSortCondition {
    LATEST,      // 최신순
    OLDEST,      // 오래된순
    PRICE_ASC,   // 낮은가격순
    PRICE_DESC,  // 높은가격순
    MOST_WISHED, // 높은즐겨찾기순
    MOST_TRADED, // TODO: 높은거래량순
}