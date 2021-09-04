package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.ItemDto;
import com.ducks.goodsduck.admin.model.dto.idol.IdolGroupDto;
import com.ducks.goodsduck.admin.model.dto.idol.IdolMemberDto;
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
    private final PostRepository postRepository;
    private final IdolGroupRepository idolGroupRepository;
    private final IdolMemberRepository idolMemberRepository;

    private final ImageUploadService imageUploadService;

    @GetMapping("/")
    public String home(Model model) {
        model = menuService.home(model);
        return "index";
    }

    @GetMapping("/items")
    public String item(Model model) {
        List<ItemDto> items = itemRepository.findAll()
                .stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
        model.addAttribute("items", items);
        return "item_list";
    }

    @GetMapping("/idol-groups")
    public String idolGroup(Model model) {
        List<IdolGroupDto> idolGroups = idolGroupRepository.findAll()
                .stream()
                .map(idolGroup -> new IdolGroupDto(idolGroup))
                .collect(Collectors.toList());
        model.addAttribute("idolGroups", idolGroups);
        return "idolGroup_list";
    }

    @GetMapping("/idol-members")
    public String idolMember(Model model) {
        List<IdolMemberDto> idolMembers = idolMemberRepository.findAll()
                .stream()
                .map(idolMember -> new IdolMemberDto(idolMember))
                .collect(Collectors.toList());
        model.addAttribute("idolMembers", idolMembers);
        return "idolMember_list";
    }
}
