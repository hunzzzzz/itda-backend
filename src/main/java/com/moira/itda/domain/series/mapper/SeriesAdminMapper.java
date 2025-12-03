package com.moira.itda.domain.series.mapper;

import com.moira.itda.global.entity.GachaItem;
import com.moira.itda.global.entity.GachaSeries;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeriesAdminMapper {
    /**
     * 가챠 시리즈 등록 > 파일 ID 확인
     */
    int selectImageFileIdChk(String fileId);

    /**
     * 가챠 시리즈 등록 > 가챠 시리즈 등록
     */
    void insertGachaSeries(GachaSeries gachaSeries);

    /**
     * 가챠 시리즈 등록 > 하위 아이템 등록
     */
    void insertGachaItem(GachaItem gachaItem);

}
