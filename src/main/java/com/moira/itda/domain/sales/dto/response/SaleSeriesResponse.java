package com.moira.itda.domain.sales.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleSeriesResponse {
    private String seriesId;
    private String title;
    private List<SalesSeriesItemNameResponse> items;
}
