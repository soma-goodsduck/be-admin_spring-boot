package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
