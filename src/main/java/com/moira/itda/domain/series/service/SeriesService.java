package com.moira.itda.domain.series.service;

import com.moira.itda.domain.series.dto.response.SaleSeriesContentsResponse;
import com.moira.itda.domain.series.dto.response.SaleSeriesResponse;
import com.moira.itda.domain.series.mapper.SeriesMapper;
import com.moira.itda.global.page.OffsetPaginationInfo;
import com.moira.itda.global.page.PaginationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.moira.itda.global.page.PageSizeConstant.SALES_SERIES_PAGE_SIZE;

@RequiredArgsConstructor
@Service
public class SeriesService {
    private final PaginationHandler paginationHandler;
    private final SeriesMapper seriesMapper;

    /**
     * 판매 > 판매등록 > 가챠 시리즈 목록 검색
     */
    @Transactional(readOnly = true)
    public SaleSeriesResponse getSeriesList(int page, String keyword) {
        // [1] 파라미터 세팅
        String keywordPattern = keyword.isBlank() ? "" : "%" + keyword + "%";
        int offset = (page - 1) * SALES_SERIES_PAGE_SIZE;

        // [2] 조회
        Long totalCount = seriesMapper.selectSeriesListCntWhenSaleSearch(keywordPattern);
        List<SaleSeriesContentsResponse> contents =
                seriesMapper.selectSeriesListWhenSaleSearch(keywordPattern, SALES_SERIES_PAGE_SIZE, offset);

        // [3] 페이지네이션 객체 획득
        OffsetPaginationInfo offsetPaginationInfo =
                paginationHandler.getOffsetPaginationInfo(totalCount, page, SALES_SERIES_PAGE_SIZE);

        // [4] DTO 병합
        return new SaleSeriesResponse(contents, offsetPaginationInfo);
    }
}
