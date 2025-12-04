package com.moira.itda.domain.sales.mapper;

import com.moira.itda.domain.sales.dto.response.SalesSeriesItemIdNameResponse;
import com.moira.itda.domain.sales.dto.response.SaleSeriesResponse;
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
}
