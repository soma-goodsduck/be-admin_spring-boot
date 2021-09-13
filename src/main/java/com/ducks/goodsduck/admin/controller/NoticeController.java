package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.CheckDto;
import com.ducks.goodsduck.admin.model.dto.NoticeEdit;
import com.ducks.goodsduck.admin.model.dto.NoticeUpload;
import com.ducks.goodsduck.admin.model.entity.Notice;
import com.ducks.goodsduck.admin.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice/{noticeId}")
    public String getNotice(@PathVariable("noticeId") Long noticeId, Model model) {
        Notice notice = noticeRepository.findById(noticeId).get();
        model.addAttribute("notice", notice);
        return "notice_detail";
    }

    @GetMapping("/notice/edit/{noticeId}")
    public String getNoticeEditForm(@PathVariable("noticeId") Long noticeId, Model model) {
        Notice notice = noticeRepository.findById(noticeId).get();
        model.addAttribute("notice", notice);
        return "notice_edit";
    }

    @PostMapping("/notice/edit/{noticeId}")
    public String editNotice(@PathVariable("noticeId") Long noticeId,
                             @ModelAttribute NoticeEdit noticeEdit, Model model) {

        Notice notice = noticeRepository.findById(noticeId).get();
        notice.setTitle(noticeEdit.getTitle());
        notice.setContent(noticeEdit.getContent());

        model.addAttribute("type", "noticeEdit");
        model.addAttribute("message", "공지사항 수정이 완료되었습니다.");

        return "message";
    }

    @GetMapping("/notice/upload")
    public String getNoticeUploadForm(Model model) {
        return "notice_upload";
    }

    @PostMapping("/notice/upload")
    public String uploadNotice(@ModelAttribute NoticeUpload noticeUpload, Model model) {

        Notice notice = new Notice(noticeUpload);
        noticeRepository.save(notice);
        
        model.addAttribute("type", "noticeUpload");
        model.addAttribute("message", "공지사항 업로드가 완료되었습니다.");

        return "message";
    }

    @GetMapping("/notice/delete/{noticeId}")
    @ResponseBody
    public CheckDto deleteNotice(@PathVariable("noticeId") Long noticeId, Model model) {
        Notice notice = noticeRepository.findById(noticeId).get();
        noticeRepository.delete(notice);
        return new CheckDto(1L);
    }
}
