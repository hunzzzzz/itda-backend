package com.moira.itda.domain.sales.mapper;

import com.moira.itda.domain.sales.dto.response.SaleSeriesResponse;
import com.moira.itda.domain.sales.dto.response.SalesSeriesItemIdNameResponse;
import com.moira.itda.global.entity.Sales;
import com.moira.itda.global.entity.SalesItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesSeriesMapper {
    // 판매 > 판매등록 > 가챠 시리즈 목록 검색 > 총 개수 계산 (페이지네이션)
    Long selectSeriesListCnt(String keywordPattern);

    // 판매 > 판매등록 > 가챠 시리즈 목록 검색
    List<SaleSeriesResponse> selectSeriesList(String keywordPattern, int pageSize, int offset);

    // 판매 > 판매등록 > 가챠 시리즈 하위 아이템 목록 조회
    List<SalesSeriesItemIdNameResponse> selectSeriesItemList(String seriesId);

    // 판매 > 판매등록 > 가챠 시리즈 ID 유효성 검사
    int selectSeriesIdChk(String seriesId);

    // 판매 > 판매등록 > 파일 ID 유효성 검사
    int selectFileIdChk(String fileId);

    // 판매 > 판매등록 > Sales 객체 저장
    void insertSales(Sales sales);

    // 판매 > 판매등록 > SalesItem 객체 저장
    void insertSalesItem(SalesItem salesItem);
}
