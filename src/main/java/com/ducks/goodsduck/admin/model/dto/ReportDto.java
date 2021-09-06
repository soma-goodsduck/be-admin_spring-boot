package com.ducks.goodsduck.admin.model.dto;

import com.ducks.goodsduck.admin.model.entity.category.Category;
import com.ducks.goodsduck.admin.model.entity.report.Report;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {

    private Long id;
    private UserDto sender;
    private String content;
    private UserDto receiver;
    private Category category;
    private LocalDateTime createdAt;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.content = report.getContent();
        this.category = report.getReportCategory();
        this.createdAt = report.getCreatedAt();
    }
}
