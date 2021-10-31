package com.ducks.goodsduck.admin.repository.report;

import com.ducks.goodsduck.admin.model.entity.report.ChatReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatReportRepository extends JpaRepository<ChatReport, Long> {
}
