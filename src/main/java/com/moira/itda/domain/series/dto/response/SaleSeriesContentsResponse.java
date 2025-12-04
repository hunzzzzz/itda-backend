package com.moira.itda.domain.series.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleSeriesContentsResponse {
    private String seriesId;
    private String title;
    private List<ItemNameResponse> items;
}
