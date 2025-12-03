package com.moira.itda.global.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class ImageFile {
    private Long id;
    private String fileId;
    private String identifier;
    private String originalFileName;
    private String storedFileName;
    private Long size;
    private String fileUrl;
    private ZonedDateTime createdAt;
}
