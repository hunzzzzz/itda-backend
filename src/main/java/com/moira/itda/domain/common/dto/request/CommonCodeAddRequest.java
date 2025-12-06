package com.moira.itda.domain.common.dto.request;

import java.util.List;

public record CommonCodeAddRequest(
        String codeKey,
        List<CommonCodeDetailAddRequest> details
) {
}
