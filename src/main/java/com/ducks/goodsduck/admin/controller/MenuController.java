package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.CategoryDto;
import com.ducks.goodsduck.admin.model.dto.ItemDto;
import com.ducks.goodsduck.admin.model.dto.ReportDto;
import com.ducks.goodsduck.admin.model.dto.UserDto;
import com.ducks.goodsduck.admin.model.dto.idol.IdolGroupDto;
import com.ducks.goodsduck.admin.model.dto.idol.IdolMemberDto;
import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.repository.ReportRepository;
import com.ducks.goodsduck.admin.repository.category.ItemCategoryRepository;
import com.ducks.goodsduck.admin.repository.idol.IdolGroupRepository;
import com.ducks.goodsduck.admin.repository.ItemRepository;
import com.ducks.goodsduck.admin.repository.PostRepository;
import com.ducks.goodsduck.admin.repository.UserRepository;
import com.ducks.goodsduck.admin.repository.idol.IdolMemberRepository;
import com.ducks.goodsduck.admin.service.ImageUploadService;
import com.ducks.goodsduck.admin.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuController {

    private final MenuService menuService;
    private final ImageUploadService imageUploadService;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PostRepository postRepository;
    private final IdolGroupRepository idolGroupRepository;
    private final IdolMemberRepository idolMemberRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ReportRepository reportRepository;

    @GetMapping("/")
    public String home(Model model) {
        model = menuService.home(model);
        return "index";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        List<ItemDto> items = itemRepository.findAll()
                .stream()
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
        categories.add(new CategoryDto("item-report", "굿즈 / 채팅 신고"));
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
                    return reportDto;
                })
                .collect(Collectors.toList());

        model.addAttribute("reports", reports);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(reports.get(0).getCategory().getName());

        return "report_list";
    }
}
