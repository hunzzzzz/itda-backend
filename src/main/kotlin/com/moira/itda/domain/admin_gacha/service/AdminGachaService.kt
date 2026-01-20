package com.moira.itda.domain.admin_gacha.service

import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaAddRequest
import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaItemAddRequest
import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaItemUpdateRequest
import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaUpdateRequest
import com.moira.itda.domain.admin_gacha.dto.response.AdminGachaItemResponse
import com.moira.itda.domain.admin_gacha.dto.response.AdminGachaResponse
import com.moira.itda.domain.admin_gacha.mapper.AdminGachaMapper
import com.moira.itda.global.entity.Gacha
import com.moira.itda.global.entity.GachaItem
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AdminGachaService(
    private val mapper: AdminGachaMapper
) {
    /**
     * 가챠 등록 > 유효성 검사
     */
    private fun validateImageFileCnt(request: AdminGachaAddRequest) {
        // 가챠 등록 시, 하나의 이미지 파일만 등록할 수 있다.
        val count = mapper.selectImageFileCnt(fileId = request.fileId)

        if (count < 1) {
            throw ItdaException(ErrorCode.NO_GACHA_IMAGE)
        } else if (count > 1) {
            throw ItdaException(ErrorCode.EXCEEDED_GACHA_IMAGE)
        }
    }

    /**
     * 가챠 등록
     */
    @Transactional
    fun add(request: AdminGachaAddRequest) {
        // [1] 유효성 검사
        this.validateImageFileCnt(request)

        // [2] Gacha 저장
        val gachaId = UUID.randomUUID().toString()
        val gacha = Gacha.from(gachaId = gachaId, request = request)
        mapper.insertGacha(gacha = gacha)

        // [3] GachaItem 저장
        for (itemRequest in request.items) {
            val gachaItem = GachaItem.from(gachaId = gachaId, request = itemRequest)
            mapper.insertGachaItem(gachaItem = gachaItem)
        }
    }

    /**
     * 가챠아이템 등록
     */
    @Transactional
    fun addItem(gachaId: String, request: AdminGachaItemAddRequest) {
        val gachaItem = GachaItem.from(gachaId = gachaId, request = request)
        mapper.insertGachaItem(gachaItem = gachaItem)
    }

    /**
     * 가챠 목록 조회
     */
    @Transactional(readOnly = true)
    fun getAll(keyword: String): List<AdminGachaResponse> {
        return mapper.selectGachaList(keyword = keyword)
    }

    /**
     * 가챠아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getItems(gachaId: String): List<AdminGachaItemResponse> {
        return mapper.selectGachaItemList(gachaId = gachaId)
    }

    /**
     * 가챠 수정
     */
    @Transactional
    fun update(gachaId: String, request: AdminGachaUpdateRequest) {
        mapper.updateGacha(gachaId = gachaId, newTitle = request.title, newManufacturer = request.manufacturer)
    }

    /**
     * 가챠아이템 수정
     */
    @Transactional
    fun updateItem(gachaItemId: Long, request: AdminGachaItemUpdateRequest) {
        mapper.updateGachaItem(gachaItemId = gachaItemId, newName = request.name)
    }

    /**
     * 가챠아이템 삭제
     */
    @Transactional
    fun deleteItem(gachaItemId: Long) {
        mapper.deleteGachaItem(gachaItemId = gachaItemId)
    }
}