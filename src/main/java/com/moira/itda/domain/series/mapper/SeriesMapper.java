package com.moira.itda.domain.series.mapper;

import com.moira.itda.domain.series.dto.response.ItemIdNameResponse;
import com.moira.itda.domain.series.dto.response.SaleSeriesContentsResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeriesMapper {
    // 판매 > 판매등록 > 가챠 시리즈 목록 검색 > 총 개수 계산 (페이지네이션)
    Long selectSeriesListCntWhenSaleSearch(String keywordPattern);

    // 판매 > 판매등록 > 가챠 시리즈 목록 검색
    List<SaleSeriesContentsResponse> selectSeriesListWhenSaleSearch(String keywordPattern, int pageSize, int offset);

    // 판매 > 판매등록 > 가챠 시리즈 하위 아이템 목록 조회
    List<ItemIdNameResponse> selectSeriesItemListWhenSale(String seriesId);
}
