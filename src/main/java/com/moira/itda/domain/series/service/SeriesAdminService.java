package com.moira.itda.domain.series.service;

import com.moira.itda.domain.series.dto.request.SeriesAddRequest;
import com.moira.itda.domain.series.mapper.SeriesAdminMapper;
import com.moira.itda.global.entity.GachaSeries;
import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SeriesAdminService {
    private final SeriesAdminMapper seriesAdminMapper;

    /**
     * 가챠 시리즈 등록 > 가챠 시리즈 등록
     */
    @Transactional
    public void add(SeriesAddRequest request) {
        String seriesId = UUID.randomUUID().toString();
        GachaSeries gachaSeries = GachaSeries.fromSeriesAddRequest(seriesId, request);

        // [1] 유효성 검사 (파일 ID)
        this.validate(request);

        // [2] 가챠 시리즈 저장
        seriesAdminMapper.insertGachaSeries(gachaSeries);

        // [3] 가챠 아이템 저장
        request.items().stream()
                .map(item -> item.toGachaItem(seriesId))
                .forEach(seriesAdminMapper::insertGachaItem);
    }

    /**
     * 가챠 시리즈 등록 > 유효성 검사
     */
    private void validate(SeriesAddRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_TITLE);
        }
        if (request.price() == null || request.price() <= 0) {
            throw new ItdaException(ErrorCode.INVALID_PRICE);
        }
        if (request.fileId() == null || request.fileId().isBlank()) {
            throw new ItdaException(ErrorCode.NO_FILE_ID);
        }
        if (request.items().isEmpty()) {
            throw new ItdaException(ErrorCode.NO_GACHA_ITEMS);
        }

        String fileId = request.fileId();
        int result = seriesAdminMapper.selectImageFileIdChk(fileId);

        if (result < 1) {
            throw new ItdaException(ErrorCode.FILE_NOT_FOUND);
        } else if (result > 1) {
            throw new ItdaException(ErrorCode.SERIES_EXCEEDED_MAX_FILE_COUNT);
        }
    }
}
