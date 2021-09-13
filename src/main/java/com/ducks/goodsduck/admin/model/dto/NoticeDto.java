package com.ducks.goodsduck.admin.model.dto;

import com.ducks.goodsduck.admin.model.entity.Notice;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class NoticeDto {

    private Long id;
    private String title;
    private String content;
    private String uploadDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoticeDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
        this.uploadDate = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
