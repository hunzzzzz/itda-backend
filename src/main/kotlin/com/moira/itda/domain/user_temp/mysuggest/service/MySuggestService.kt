package com.moira.itda.domain.user_temp.mysuggest.service

import com.moira.itda.domain.user_temp.mysuggest.mapper.MySuggestMapper
import com.moira.itda.global.entity.TradeSuggestStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MySuggestService(
    private val mySuggestMapper: MySuggestMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 취소
     */
    @Transactional
    fun cancelSuggest(userId: String, suggestId: String) {
        // [1] status 조회
        val status = mySuggestMapper.selectTradeSuggestStatus(userId = userId, suggestId = suggestId)
            ?: throw ItdaException(ErrorCode.SUGGEST_NOT_FOUND)

        // [2] status에 대한 유효성 검사
        when (status) {
            TradeSuggestStatus.APPROVED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_APPROVED_SUGGEST)
            }

            TradeSuggestStatus.REJECTED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_REJECTED_SUGGEST)
            }

            TradeSuggestStatus.CANCELED.name -> {
                throw ItdaException(ErrorCode.ALREADY_CANCELED_SUGGEST)
            }
        }

        // [3] 거래제안 취소
        mySuggestMapper.updateTradeSuggestStatusCanceled(userId = userId, suggestId = suggestId)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 삭제
     */
    @Transactional
    fun deleteSuggest(userId: String, suggestId: String) {
        // [1] status 조회
        val status = mySuggestMapper.selectTradeSuggestStatus(userId = userId, suggestId = suggestId)
            ?: throw ItdaException(ErrorCode.SUGGEST_NOT_FOUND)

        // [2] status에 대한 유효성 검사
        when (status) {
            TradeSuggestStatus.APPROVED.name -> {
                throw ItdaException(ErrorCode.CANNOT_DELETE_APPROVED_SUGGEST)
            }

            TradeSuggestStatus.PENDING.name -> {
                throw ItdaException(ErrorCode.CANNOT_DELETE_PENDING_SUGGEST)
            }
        }

        // [3] TradeSuggest 삭제
        mySuggestMapper.deleteTradeSuggest(userId = userId, suggestId = suggestId)
    }
}