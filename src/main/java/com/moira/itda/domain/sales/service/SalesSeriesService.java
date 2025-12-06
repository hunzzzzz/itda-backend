package com.moira.itda.domain.sales.service;

import com.moira.itda.domain.sales.dto.request.SalesAddRequest;
import com.moira.itda.domain.sales.dto.request.SalesItemAddRequest;
import com.moira.itda.domain.sales.dto.response.SaleSeriesResponse;
import com.moira.itda.domain.sales.dto.response.SaleSeriesSearchResponse;
import com.moira.itda.domain.sales.dto.response.SalesResponse;
import com.moira.itda.domain.sales.dto.response.SalesSeriesItemIdNameResponse;
import com.moira.itda.domain.sales.mapper.SalesSeriesMapper;
import com.moira.itda.global.auth.SimpleUserAuth;
import com.moira.itda.global.entity.Sales;
import com.moira.itda.global.entity.SalesHopeMethod;
import com.moira.itda.global.entity.SalesItem;
import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import com.moira.itda.global.page.OffsetPaginationInfo;
import com.moira.itda.global.page.PaginationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    /**
     * 판매 > 판매등록
     */
    @Transactional
    public void addSales(
            SimpleUserAuth userAuth,
            String seriesId,
            SalesAddRequest request
    ) {
        String salesId = UUID.randomUUID().toString();
        String userId = userAuth.userId();

        // [1] 유효성 검사
        this.validate(seriesId, request);

        // [2] Sales 객체 저장
        Sales sales = Sales.fromSalesAddRequest(salesId, seriesId, userId, request);
        salesSeriesMapper.insertSales(sales);

        // [3] SalesItem 객체 저장
        for (SalesItemAddRequest itemRequest : request.items()) {
            SalesItem salesItem = SalesItem.fromSalesItemAddRequest(
                    seriesId,
                    salesId,
                    itemRequest
            );
            salesSeriesMapper.insertSalesItem(salesItem);
        }
    }

    /**
     * 판매 > 판매등록 > 유효성 검사
     */
    private void validate(String seriesId, SalesAddRequest request) {
        if (salesSeriesMapper.selectSeriesIdChk(seriesId) < 1) {
            throw new ItdaException(ErrorCode.SERIES_NOT_FOUND);
        }
        if (salesSeriesMapper.selectFileIdChk(request.fileId()) < 1) {
            throw new ItdaException(ErrorCode.FILE_NOT_FOUND);
        }
        if (request.title() == null || request.title().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_SALES_TITLE);
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_SALES_CONTENT);
        }
        try {
            SalesHopeMethod.valueOf(request.hopeMethod());
        } catch (Exception e) {
            throw new ItdaException(ErrorCode.INVALID_SALES_HOPE_METHOD);
        }
    }

    /**
     * 판매 > 판매 목록 조회
     */
    public List<SalesResponse> getSalesList(SimpleUserAuth userAuth) {
        return salesSeriesMapper.selectSalesList(userAuth.userId());
    }
}
