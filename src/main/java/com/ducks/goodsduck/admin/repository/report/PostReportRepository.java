package com.ducks.goodsduck.admin.repository.report;

import com.ducks.goodsduck.admin.model.entity.report.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
}