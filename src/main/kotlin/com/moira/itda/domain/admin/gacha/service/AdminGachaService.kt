package com.moira.itda.domain.admin.gacha.service

import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaAddRequest
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaItemAddRequest
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaItemUpdateRequest
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaUpdateRequest
import com.moira.itda.domain.admin.gacha.dto.response.AdminGachaItemResponse
import com.moira.itda.domain.admin.gacha.dto.response.AdminGachaResponse
import com.moira.itda.domain.admin.gacha.mapper.AdminGachaMapper
import com.moira.itda.global.entity.Gacha
import com.moira.itda.global.entity.GachaItem
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AdminGachaService(
    private val adminGachaMapper: AdminGachaMapper,
    private val awsS3Handler: AwsS3Handler
) {
    /**
     * 어드민 페이지 > 가챠정보 > 등록 > 유효성 검사
     */
    private fun validate(request: AdminGachaAddRequest) {
        // 필드값 체크
        if (request.title.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_GACHA_TITLE)
        }
        if (request.price <= 0) {
            throw ItdaException(ErrorCode.INVALID_GACHA_PRICE)
        }
        if (request.fileId.isBlank()) {
            throw ItdaException(ErrorCode.INVALID_GACHA_FILE_ID)
        }
        if (request.items.isEmpty()) {
            throw ItdaException(ErrorCode.NO_GACHA_ITEMS)
        }
        for (item in request.items) {
            if (item.name.isBlank()) {
                throw ItdaException(ErrorCode.INVALID_GACHA_ITEM_NAME)
            }
        }

        // 파일 개수 체크
        val count = adminGachaMapper.selectImageFileChk(fileId = request.fileId)

        if (count < 1) {
            throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        } else if (count > 1) {
            throw ItdaException(ErrorCode.GACHA_EXCEEDED_MAX_FILE_COUNT)
        }
    }

    /**
     * 어드민 페이지 > 가챠정보 > 등록
     */
    @Transactional
    fun add(request: AdminGachaAddRequest) {
        // [1] 유효성 검사
        this.validate(request)

        // [2] Gacha 저장
        val gachaId = UUID.randomUUID().toString()
        val gacha = Gacha.fromAdminGachaAddRequest(gachaId = gachaId, request = request)

        adminGachaMapper.insertGacha(gacha = gacha)

        // [3] GachaItem 저장
        for (itemRequest in request.items) {
            val gachaItem = GachaItem.fromAdminGachaItemAddRequest(gachaId = gachaId, request = itemRequest)
            adminGachaMapper.insertGachaItem(gachaItem = gachaItem)
        }
    }

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 등록
     */
    @Transactional
    fun addItem(gachaId: String, request: AdminGachaItemAddRequest) {
        val gachaItem = GachaItem.fromAdminGachaItemAddRequest(gachaId = gachaId, request = request)
        adminGachaMapper.insertGachaItem(gachaItem = gachaItem)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 전체 조회
     */
    @Transactional(readOnly = true)
    fun getAll(keyword: String): List<AdminGachaResponse> {
        val keywordPattern = if (keyword.isBlank()) "" else "%$keyword%"

        return adminGachaMapper.selectGachaList(keywordPattern = keywordPattern)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getItems(gachaId: String): List<AdminGachaItemResponse> {
        return adminGachaMapper.selectGachaItemList(gachaId = gachaId)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 수정
     */
    @Transactional
    fun update(gachaId: String, request: AdminGachaUpdateRequest) {
        adminGachaMapper.updateGacha(gachaId = gachaId, request = request)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 수정
     */
    @Transactional
    fun updateItem(gachaId: String, itemId: Long, request: AdminGachaItemUpdateRequest) {
        adminGachaMapper.updateGachaItem(itemId = itemId, gachaId = gachaId, newName = request.name)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 삭제
     */
    @Transactional
    fun delete(gachaId: String) {
        // [1] 파일 ID, 파일 Url 조회
        val fileId = adminGachaMapper.selectFileId(gachaId = gachaId)
            ?: throw ItdaException(ErrorCode.FILE_NOT_FOUND)
        val fileUrls = adminGachaMapper.selectFileUrlList(fileId = fileId)

        // [2] 파일 삭제 (AWS S3)
        fileUrls.forEach { fileUrl -> awsS3Handler.delete(fileUrl = fileUrl) }

        // [2] ImageFile -> GachaItem -> Gacha 삭제
        adminGachaMapper.deleteImageFile(fileId = fileId)
        adminGachaMapper.deleteGachaItemList(gachaId = gachaId)
        adminGachaMapper.deleteGacha(gachaId = gachaId)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 삭제
     */
    @Transactional
    fun deleteItem(gachaId: String, itemId: Long) {
        adminGachaMapper.deleteGachaItem(gachaId = gachaId, itemId = itemId)
    }
}