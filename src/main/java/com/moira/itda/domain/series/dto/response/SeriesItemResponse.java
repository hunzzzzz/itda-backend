package com.moira.itda.domain.series.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesItemResponse {
    private Long seriesItemId;
    private String name;
}
