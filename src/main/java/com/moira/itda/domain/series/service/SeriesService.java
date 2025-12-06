package com.moira.itda.domain.series.service;

import com.moira.itda.domain.series.dto.request.SeriesAddRequest;
import com.moira.itda.domain.series.dto.request.SeriesItemAddRequest;
import com.moira.itda.domain.series.dto.response.SeriesDetailResponse;
import com.moira.itda.domain.series.dto.response.SeriesPageResponse;
import com.moira.itda.domain.series.dto.response.SeriesResponse;
import com.moira.itda.domain.series.mapper.SeriesMapper;
import com.moira.itda.global.entity.GachaItem;
import com.moira.itda.global.entity.GachaSeries;
import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import com.moira.itda.global.page.OffsetPaginationInfo;
import com.moira.itda.global.page.PaginationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.moira.itda.global.page.PageSizeConstant.SERIES_PAGE_SIZE;

@RequiredArgsConstructor
@Service
public class SeriesService {
    private final PaginationHandler paginationHandler;
    private final SeriesMapper seriesMapper;

    /**
     * 가챠시리즈 > 등록 > 유효성 검사
     */
    private void validate(SeriesAddRequest request) {
        // [1] 각 필드에 대한 유효성 검사
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

        // [2] 파일 존재 여부 및 업로드 개수(1개) 확인
        String fileId = request.fileId();
        int result = seriesMapper.selectFileIdChk(fileId);

        if (result < 1) {
            throw new ItdaException(ErrorCode.FILE_NOT_FOUND);
        } else if (result > 1) {
            throw new ItdaException(ErrorCode.SERIES_EXCEEDED_MAX_FILE_COUNT);
        }
    }

    /**
     * 가챠시리즈 > 등록
     */
    @Transactional
    public void add(SeriesAddRequest request) {
        String seriesId = UUID.randomUUID().toString();

        // [1] 유효성 검사
        this.validate(request);

        // [2] GachaSeries 저장
        GachaSeries gachaSeries = GachaSeries.fromSeriesAddRequest(seriesId, request);
        seriesMapper.insertGachaSeries(gachaSeries);

        // [3] GachaItem 저장
        for (SeriesItemAddRequest itemRequest : request.items()) {
            GachaItem gachaItem = GachaItem.fromSeriesItemAddRequest(seriesId, itemRequest);
            seriesMapper.insertGachaItem(gachaItem);
        }
    }

    /**
     * 가챠시리즈 > 전체 조회 (오프셋 기반 페이지네이션)
     */
    @Transactional(readOnly = true)
    public SeriesPageResponse get(String keyword, int page) {
        // [1] 변수 세팅
        String keywordPattern = keyword.isEmpty() ? "" : "%" + keyword + "%";
        int offset = (page - 1) * SERIES_PAGE_SIZE;

        // [2] 조회
        Long totalCount = seriesMapper.selectGachaSeriesListCntWithPagination(keywordPattern);
        List<SeriesResponse> seriesList = seriesMapper.selectGachaSeriesListWithPagination(keywordPattern, SERIES_PAGE_SIZE, offset);

        // [3] 오프셋 페이지네이션 응답값 적용
        OffsetPaginationInfo paginationInfo = paginationHandler.getOffsetPaginationInfo(
                totalCount, page, SERIES_PAGE_SIZE
        );

        return new SeriesPageResponse(seriesList, paginationInfo);
    }

    /**
     * 가챠시리즈 > 단건 조회
     */
    @Transactional(readOnly = true)
    public List<SeriesDetailResponse> get(String seriesId) {
        return seriesMapper.selectGachaSeries(seriesId);
    }
}
