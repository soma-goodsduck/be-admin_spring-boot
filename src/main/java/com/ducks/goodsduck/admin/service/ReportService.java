package com.ducks.goodsduck.admin.service;

import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.model.entity.report.Report;
import com.ducks.goodsduck.admin.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;

    public Long delete(Long reportId) {

        try {
            Report deleteReport = reportRepository.findById(reportId).orElseGet(null);
            if(deleteReport == null) {
                return -1L;
            }

            reportRepository.delete(deleteReport);

            return 1L;
        } catch (Exception e) {
            return -1L;
        }
    }

    public Long check(Long reportId) {

        try {
            Report report = reportRepository.findById(reportId).get();
            User receiver = report.getReceiver();

            List<Report> reports = reportRepository.findByUserId(receiver.getId());
            receiver.increaseReportCount();

            reportRepository.deleteAllInBatch(reports);

            return 1L;
        } catch (Exception e) {
            return -1L;
        }
    }
}
