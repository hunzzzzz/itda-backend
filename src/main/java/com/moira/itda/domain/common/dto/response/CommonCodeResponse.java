package com.moira.itda.domain.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeResponse {
    private String codeKey;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<CommonCodeDetailResponse> details;
}
