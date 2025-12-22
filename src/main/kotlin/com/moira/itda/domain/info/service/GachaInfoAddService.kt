package com.moira.itda.domain.info.service

import com.moira.itda.domain.info.dto.request.GachaInfoAddRequest
import com.moira.itda.domain.info.mapper.GachaInfoAddMapper
import com.moira.itda.global.entity.GachaInfoAdd
import com.moira.itda.global.entity.GachaInfoType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaInfoAddService(
    private val gachaInfoAddMapper: GachaInfoAddMapper
) {
    /**
     * 정보등록요청 탭 > 정보등록요청 (유효성 검사)
     */
    private fun validate(request: GachaInfoAddRequest) {
        kotlin.runCatching { GachaInfoType.valueOf(request.type) }.onFailure {
            throw ItdaException(ErrorCode.INVALID_GACHA_INFO_TYPE)
        }
        if (request.content.isBlank()) {
            throw ItdaException(ErrorCode.GACHA_INFO_CONTENT_IS_MANDATORY)
        }
        if (request.fileId != null) {
            if (gachaInfoAddMapper.selectFileIdChk(fileId = request.fileId) < 1) {
                throw ItdaException(ErrorCode.FILE_NOT_FOUND)
            }
        }
        if (request.receiveEmailYn != "Y" && request.receiveEmailYn != "N") {
            throw ItdaException(ErrorCode.INVALID_GACHA_INFO_MESSAGE_YN)
        }
    }

    /**
     * 정보등록요청 탭 > 정보등록요청
     */
    @Transactional
    fun add(userId: String, request: GachaInfoAddRequest) {
        // [1] 유효성 검사
        this.validate(request = request)

        // [2] 저장
        val gachaInfoAdd = GachaInfoAdd.fromGachaInfoAddRequest(userId = userId, request = request)

        gachaInfoAddMapper.insertGachaInfoAdd(gachaInfoAdd = gachaInfoAdd)
    }
}