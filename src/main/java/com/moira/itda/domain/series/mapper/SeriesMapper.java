package com.moira.itda.domain.series.mapper;

import com.moira.itda.domain.series.dto.response.SeriesResponse;
import com.moira.itda.global.entity.GachaItem;
import com.moira.itda.global.entity.GachaSeries;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeriesMapper {
    /**
     * 가챠시리즈 > 등록 > 파일 ID 존재 여부 확인
     */
    int selectFileIdChk(String fileId);

    // ------------------------------------------------------------------------

    /**
     * 가챠시리즈 > 등록 > GachaSeries 저장
     */
    void insertGachaSeries(GachaSeries gachaSeries);

    /**
     * 가챠시리즈 > 등록 > GachaItem 저장
     */
    void insertGachaItem(GachaItem gachaItem);

    // ------------------------------------------------------------------------

    /**
     * 가챠시리즈 > 전체 조회
     */
    Long selectGachaSeriesListCnt(String keywordPattern);

    /**
     * 가챠시리즈 > 전체 조회
     */
    List<SeriesResponse> selectGachaSeriesList(String keywordPattern, int pageSize, int offset);
}
