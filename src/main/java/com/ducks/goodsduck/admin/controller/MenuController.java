package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.*;
import com.ducks.goodsduck.admin.model.dto.idol.IdolGroupDto;
import com.ducks.goodsduck.admin.model.dto.idol.IdolMemberDto;
import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.repository.*;
import com.ducks.goodsduck.admin.repository.category.ItemCategoryRepository;
import com.ducks.goodsduck.admin.repository.idol.IdolGroupRepository;
import com.ducks.goodsduck.admin.repository.idol.IdolMemberRepository;
import com.ducks.goodsduck.admin.service.ImageUploadService;
import com.ducks.goodsduck.admin.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuController {

    private final MenuService menuService;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final IdolGroupRepository idolGroupRepository;
    private final IdolMemberRepository idolMemberRepository;
    private final ReportRepository reportRepository;
    private final NoticeRepository noticeRepository;

    @GetMapping("/")
    public String home(Model model) {
        model = menuService.home(model);
        return "index";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        List<ItemDto> items = itemRepository.findAll()
                .stream()
                .filter(item -> item.getDeletedAt() == null)
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
        model.addAttribute("items", items);
        return "item_list";
    }

    @GetMapping("/idol-groups")
    public String getIdolGroups(Model model) {
        List<IdolGroupDto> idolGroups = idolGroupRepository.findAll()
                .stream()
                .map(idolGroup -> new IdolGroupDto(idolGroup))
                .collect(Collectors.toList());
        model.addAttribute("idolGroups", idolGroups);
        return "idolGroup_list";
    }

    @GetMapping("/idol-members")
    public String getIdolMembers(Model model) {
        List<IdolMemberDto> idolMembers = idolMemberRepository.findAll()
                .stream()
                .map(idolMember -> new IdolMemberDto(idolMember))
                .collect(Collectors.toList());
        model.addAttribute("idolMembers", idolMembers);
        return "idolMember_list";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(new CategoryDto("item", "굿즈"));
        categories.add(new CategoryDto("post", "포스트"));
        categories.add(new CategoryDto("user-report", "유저 신고"));
        categories.add(new CategoryDto("item-report", "굿즈 신고"));
        categories.add(new CategoryDto("chat-report", "채팅 신고"));
        categories.add(new CategoryDto("post-report", "포스트 신고"));
        categories.add(new CategoryDto("comment-report", "댓글 신고"));

        model.addAttribute("categories", categories);

        return "category_select";
    }

    @GetMapping("/reports")
    public String getReports(Model model) {

        List<ReportDto> reports = reportRepository.findAll()
                .stream()
                .map(report -> {
                    ReportDto reportDto = new ReportDto(report);
                    reportDto.setReceiver(new UserDto(report.getReceiver()));
                    User sender = userRepository.findById(report.getSenderId()).get();
                    reportDto.setSender(new UserDto(sender));
                    reportDto.setCategoryName(report.getReportCategory().getName());
                    return reportDto;
                })
                .collect(Collectors.toList());

        model.addAttribute("reports", reports);

        return "report_list";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        model.addAttribute("type", "logout");
        model.addAttribute("message", "로그아웃이 완료되었습니다.");
        return "message";
    }

    @GetMapping("/notices")
    public String getNotices(Model model) {
        List<NoticeDto> notices = noticeRepository.findAll()
                .stream()
                .map(notice -> new NoticeDto(notice))
                .collect(Collectors.toList());
        model.addAttribute("notices", notices);
        return "notice_list";
    }
}
