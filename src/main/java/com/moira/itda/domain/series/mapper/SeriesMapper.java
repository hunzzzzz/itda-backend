package com.moira.itda.domain.series.mapper;

import com.moira.itda.domain.series.dto.response.SaleSeriesContentsResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeriesMapper {
    // 판매 > 판매등록 > 검색 > 총 개수 계산
    Long selectSeriesListCntWhenSaleSearch(String keywordPattern);

    // 판매 > 판매등록 > 검색
    List<SaleSeriesContentsResponse> selectSeriesListWhenSaleSearch(String keywordPattern, int pageSize, int offset);
}
