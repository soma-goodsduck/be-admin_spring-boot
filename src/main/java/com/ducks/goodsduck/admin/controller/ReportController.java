package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.CheckDto;
import com.ducks.goodsduck.admin.model.dto.ReportDto;
import com.ducks.goodsduck.admin.model.dto.UserDto;
import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.repository.report.ReportRepository;
import com.ducks.goodsduck.admin.repository.UserRepository;
import com.ducks.goodsduck.admin.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportController {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReportService reportService;

    // FEAT : 신고 검색
    @GetMapping("/report")
    public String searchByUser(@RequestParam("search") String keyword, @RequestParam("type") String type, Model model,
                               @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if (type.equals("senderId")) {
            List<ReportDto> reportDtos = reportRepository.findBySenderId(Long.parseLong(keyword), pageable)
                    .stream()
                    .map(report -> {
                        ReportDto reportDto = new ReportDto(report);
                        reportDto.setReceiver(new UserDto(report.getReceiver()));
                        User sender = userRepository.findById(report.getSenderId()).get();
                        reportDto.setSender(new UserDto(sender));
                        reportDto.setCategoryName(report.getReportCategory().getName());
                        reportDto.setReportType(report.getDecriminatorValue());
                        return reportDto;
                    })
                    .collect(Collectors.toList());

            if (reportDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "report_list";
            } else {
                Page<ReportDto> reports = new PageImpl<>(reportDtos, pageable, reportDtos.size());
                model.addAttribute("reports", reports);
                return "report_list";
            }
        } else if (type.equals("senderNickName")) {
            List<ReportDto> reportDtos = reportRepository.findBySenderNickName(keyword, pageable)
                    .stream()
                    .map(report -> {
                        ReportDto reportDto = new ReportDto(report);
                        reportDto.setReceiver(new UserDto(report.getReceiver()));
                        User sender = userRepository.findById(report.getSenderId()).get();
                        reportDto.setSender(new UserDto(sender));
                        reportDto.setCategoryName(report.getReportCategory().getName());
                        reportDto.setReportType(report.getDecriminatorValue());
                        return reportDto;
                    })
                    .collect(Collectors.toList());

            if (reportDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "report_list";
            } else {
                Page<ReportDto> reports = new PageImpl<>(reportDtos, pageable, reportDtos.size());
                model.addAttribute("reports", reports);
                return "report_list";
            }
        } else if (type.equals("receiverId")) {
            List<ReportDto> reportDtos = reportRepository.findByReceiverId(Long.parseLong(keyword), pageable)
                    .stream()
                    .map(report -> {
                        ReportDto reportDto = new ReportDto(report);
                        reportDto.setReceiver(new UserDto(report.getReceiver()));
                        User sender = userRepository.findById(report.getSenderId()).get();
                        reportDto.setSender(new UserDto(sender));
                        reportDto.setCategoryName(report.getReportCategory().getName());
                        reportDto.setReportType(report.getDecriminatorValue());
                        return reportDto;
                    })
                    .collect(Collectors.toList());

            if (reportDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "report_list";
            } else {
                Page<ReportDto> reports = new PageImpl<>(reportDtos, pageable, reportDtos.size());
                model.addAttribute("reports", reports);
                return "report_list";
            }
        } else {
            List<ReportDto> reportDtos = reportRepository.findByReceiverNickName(keyword, pageable)
                    .stream()
                    .map(report -> {
                        ReportDto reportDto = new ReportDto(report);
                        reportDto.setReceiver(new UserDto(report.getReceiver()));
                        User sender = userRepository.findById(report.getSenderId()).get();
                        reportDto.setSender(new UserDto(sender));
                        reportDto.setCategoryName(report.getReportCategory().getName());
                        reportDto.setReportType(report.getDecriminatorValue());
                        return reportDto;
                    })
                    .collect(Collectors.toList());

            if (reportDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "report_list";
            } else {
                Page<ReportDto> reports = new PageImpl<>(reportDtos, pageable, reportDtos.size());
                model.addAttribute("reports", reports);
                return "report_list";
            }
        }
    }

    // FEAT : 신고 삭제
    @GetMapping("/report/delete/{reportId}")
    @ResponseBody
    public CheckDto deleteByReportId(@PathVariable("reportId") Long reportId) {
        Long success = reportService.delete(reportId);
        return new CheckDto(success);
    }

    // FEAT : 신고 처리
    @GetMapping("/report/check/{reportId}")
    @ResponseBody
    public CheckDto checkReport(@PathVariable("reportId") Long reportId) {
        Long success = reportService.check(reportId);
        return new CheckDto(success);
    }
}
