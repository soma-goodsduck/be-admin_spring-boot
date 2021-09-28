package com.ducks.goodsduck.admin.model.dto;

import com.ducks.goodsduck.admin.model.entity.category.Category;
import com.ducks.goodsduck.admin.model.entity.report.Report;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ReportDto {

    private Long id;
    private UserDto sender;
    private String content;
    private UserDto receiver;
    private String categoryName;
    private String reportDate;
    private String reportType;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.content = report.getContent();
        this.reportDate = report.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setReportType(String reportType) {
        if(reportType.equals("ItemReport")) this.reportType = "굿즈 신고";
        else if(reportType.equals("UserReport")) this.reportType = "유저 신고";
        else if(reportType.equals("ChatReport")) this.reportType = "채팅 신고";
        else if(reportType.equals("PostReport")) this.reportType = "게시글 신고";
        else if(reportType.equals("CommentReport")) this.reportType = "댓글 신고";
    }
}
