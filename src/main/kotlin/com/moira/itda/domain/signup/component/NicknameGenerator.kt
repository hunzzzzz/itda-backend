package com.moira.itda.domain.signup.component

import kotlin.random.Random

object NicknameGenerator {
    private val adjectives = listOf(
        "행복한", "희귀한", "운좋은", "빛나는", "신비한",
        "용감한", "명랑한", "포근한", "날렵한", "똑똑한",
        "즐거운", "친절한", "화려한", "수줍은", "대담한",
        "차분한", "영리한", "강력한", "섬세한", "활기찬",
        "우아한", "듬직한", "시원한", "따뜻한", "정직한"
    )
    private val nouns = listOf(
        "콜렉터", "수집가", "탐험가", "덕후", "전문가",
        "매니아", "대장", "연구가", "마스터", "꿈나무",
        "사냥꾼", "여행자", "관찰자", "기록가", "보관자",
        "감별사", "개척자", "수호자", "예술가", "장인",
        "중독자", "선구자", "심판자", "정복자", "방랑자"
    )

    /**
     * 닉네임 생성
     */
    fun generate(): String {
        return "${adjectives.random()}_${nouns.random()}_${Random.nextInt(1000, 9999)}"
    }
}