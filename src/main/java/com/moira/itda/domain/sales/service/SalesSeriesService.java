package com.moira.itda.domain.sales.service;

import com.moira.itda.domain.sales.dto.response.SalesSeriesItemIdNameResponse;
import com.moira.itda.domain.sales.dto.response.SaleSeriesResponse;
import com.moira.itda.domain.sales.dto.response.SaleSeriesSearchResponse;
import com.moira.itda.domain.sales.mapper.SalesSeriesMapper;
import com.moira.itda.global.page.OffsetPaginationInfo;
import com.moira.itda.global.page.PaginationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.moira.itda.global.page.PageSizeConstant.SALES_SERIES_PAGE_SIZE;

@RequiredArgsConstructor
@Service
public class SalesSeriesService {
    private final PaginationHandler paginationHandler;
    private final SalesSeriesMapper salesSeriesMapper;

    /**
     * 판매 > 판매등록 > 가챠 시리즈 목록 검색
     */
    @Transactional(readOnly = true)
    public SaleSeriesSearchResponse getSeriesList(int page, String keyword) {
        // [1] 파라미터 세팅
        String keywordPattern = keyword.isBlank() ? "" : "%" + keyword + "%";
        int offset = (page - 1) * SALES_SERIES_PAGE_SIZE;

        // [2] 조회
        Long totalCount = salesSeriesMapper.selectSeriesListCnt(keywordPattern);
        List<SaleSeriesResponse> contents =
                salesSeriesMapper.selectSeriesList(keywordPattern, SALES_SERIES_PAGE_SIZE, offset);

        // [3] 페이지네이션 객체 획득
        OffsetPaginationInfo offsetPaginationInfo =
                paginationHandler.getOffsetPaginationInfo(totalCount, page, SALES_SERIES_PAGE_SIZE);

        // [4] DTO 병합
        return new SaleSeriesSearchResponse(contents, offsetPaginationInfo);
    }

    /**
     * 판매 > 판매등록 > 가챠 시리즈 하위 아이템 목록 조회
     */
    public List<SalesSeriesItemIdNameResponse> getSeriesItemList(String seriesId) {
        return salesSeriesMapper.selectSeriesItemList(seriesId);
    }
}
