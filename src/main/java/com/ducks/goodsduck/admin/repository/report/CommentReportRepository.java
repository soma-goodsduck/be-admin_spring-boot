package com.ducks.goodsduck.admin.repository.report;


import com.ducks.goodsduck.admin.model.entity.report.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
}