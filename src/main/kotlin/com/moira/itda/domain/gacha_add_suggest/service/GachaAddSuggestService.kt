package com.moira.itda.domain.gacha_add_suggest.service

import com.moira.itda.domain.gacha_add_suggest.component.GachaAddSuggestValidator
import com.moira.itda.domain.gacha_add_suggest.dto.request.GachaAddSuggestRequest
import com.moira.itda.domain.gacha_add_suggest.mapper.GachaAddSuggestMapper
import com.moira.itda.global.entity.GachaAddSuggest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaAddSuggestService(
    private val mapper: GachaAddSuggestMapper,
    private val validator: GachaAddSuggestValidator
) {
    /**
     * 정보등록요청
     */
    @Transactional
    fun add(userId: String, request: GachaAddSuggestRequest) {
        // [1] 유효성 검사
        validator.validate(request = request)

        // [2] 저장
        val gachaAddSuggest = GachaAddSuggest.fromRequest(userId = userId, request = request)
        mapper.insertGachaAddSuggest(gachaAddSuggest = gachaAddSuggest)
    }
}