package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.*;
import com.ducks.goodsduck.admin.model.dto.idol.IdolGroupDto;
import com.ducks.goodsduck.admin.model.dto.idol.IdolMemberDto;
import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.repository.*;
import com.ducks.goodsduck.admin.repository.idol.IdolGroupRepository;
import com.ducks.goodsduck.admin.repository.idol.IdolMemberRepository;
import com.ducks.goodsduck.admin.repository.report.*;
import com.ducks.goodsduck.admin.service.MenuService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    private final ItemReportRepository itemReportRepository;
    private final PostReportRepository postReportRepository;
    private final CommentReportRepository commentReportRepository;
    private final ChatReportRepository chatReportRepository;
    private final UserReportRepository userReportRepository;
    private final NoticeRepository noticeRepository;

    @GetMapping("/")
    public String home(Model model) {
        menuService.home(model);
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Long totalCount = userRepository.count();
        List<UserDto> userDtos = userRepository.findAll(pageable)
                .stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());

        Page<UserDto> users = new PageImpl<>(userDtos, pageable, totalCount);
        model.addAttribute("users", users);
        return "user_list";
    }

    @GetMapping("/items")
    public String getItems(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Long totalCount = itemRepository.count();
        Page<ItemDto> itemDtos = itemRepository.findAllWithPageable(pageable)
                .map(item -> new ItemDto(item));

//        Page<ItemDto> items = new PageImpl<>(itemDtos, pageable, totalCount);
        model.addAttribute("items", itemDtos);
        return "item_list";
    }

    @GetMapping("/reports")
    public String getReports(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
//        Long totalCount = reportRepository.count();
        Page<ReportDto> reportDtos = reportRepository.findAllWithPageable(pageable)
                .map(report -> {
                    ReportDto reportDto = new ReportDto(report);
                    reportDto.setReceiver(new UserDto(report.getReceiver()));
                    User sender = userRepository.findById(report.getSenderId()).get();
                    reportDto.setSender(new UserDto(sender));
                    reportDto.setCategoryName(report.getReportCategory().getName());
                    reportDto.setReportType(report.getDecriminatorValue());

                    if(report.getDecriminatorValue().equals("ItemReport")) {
                        reportDto.setReportedId(Long.toString(itemReportRepository.findById(report.getId()).get().getItemId()));
                    } else if(report.getDecriminatorValue().equals("UserReport")) {
                        reportDto.setReportedId(Long.toString(userReportRepository.findById(report.getId()).get().getUserId()));
                    } else if(report.getDecriminatorValue().equals("ChatReport")) {
                        reportDto.setReportedId(chatReportRepository.findById(report.getId()).get().getChatId());
                    } else if(report.getDecriminatorValue().equals("PostReport")) {
                        reportDto.setReportedId(Long.toString(postReportRepository.findById(report.getId()).get().getPostId()));
                    } else if(report.getDecriminatorValue().equals("CommentReport")) {
                        reportDto.setReportedId(Long.toString(commentReportRepository.findById(report.getId()).get().getCommentId()));
                    }

                    return reportDto;
                });

//        Page<ReportDto> reports = new PageImpl<>(reportDtos, pageable, totalCount);
        model.addAttribute("reports", reportDtos);
        return "report_list";
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
    public String getIdolMembers(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Long totalCount = idolMemberRepository.count();
        List<IdolMemberDto> idolMemberDtos = idolMemberRepository.findAll(pageable)
                .stream()
                .map(idolMember -> new IdolMemberDto(idolMember))
                .collect(Collectors.toList());

        Page<IdolMemberDto> idolMembers = new PageImpl<>(idolMemberDtos, pageable, totalCount);
        model.addAttribute("idolMembers", idolMembers);
        return "idolMember_list";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(new CategoryDto("item", "굿즈"));
        categories.add(new CategoryDto("post", "포스트"));
        categories.add(new CategoryDto("community", "커뮤니티 메뉴"));
        categories.add(new CategoryDto("user-report", "유저 신고"));
        categories.add(new CategoryDto("item-report", "굿즈 신고"));
        categories.add(new CategoryDto("chat-report", "채팅 신고"));
        categories.add(new CategoryDto("post-report", "포스트 신고"));
        categories.add(new CategoryDto("comment-report", "댓글 신고"));

        model.addAttribute("categories", categories);
        return "category_select";
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

    @GetMapping("/test")
    public String check() throws IOException, InterruptedException {

        Process process = null;
        String input = "0080c02c-9811-42ad-98cb-9a6ce66dcc50.GIF";
        String output = "videotest.mp4";

        String download[] = { "sudo", "aws", "s3", "cp", "s3://goodsduck-post-image/" + input, "/test"};
        String cmd[] = { "ffmpeg", "-i", input, "-movflags", "faststart", "-pix_fmt", "yuv420p", "-vf", "\"scale=trunc(iw/2)*2:trunc(ih/2)*2\"", output };
        String delete[] = { "sudo", "rm", "-r", input};
        String upload[] = { "sudo", "aws", "s3", "cp", output, "s3://function-temp"};

        for(int i = 1; i <= 4; i++) {
            try {

                if(i == 1) process = Runtime.getRuntime().exec(download);
                else if(i == 2) process = Runtime.getRuntime().exec(cmd);
                else if(i == 3) process = Runtime.getRuntime().exec(delete);
                else if(i == 4) process = Runtime.getRuntime().exec(upload);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                process.getErrorStream().close();
                process.getInputStream().close();
                process.getOutputStream().close();

                process.waitFor();
                process.destroy();
            }
        }

        return "1";
    }
}
