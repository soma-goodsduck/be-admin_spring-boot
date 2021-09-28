package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.ItemDto;
import com.ducks.goodsduck.admin.model.dto.UserDto;
import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.repository.UserRepository;
import io.swagger.annotations.Api;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    // FEAT : 유저 검색
    @GetMapping("/user")
    public String searchByUser(@RequestParam("search") String keyword, @RequestParam("type") String type, Model model,
                               @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if(type.equals("id")) {
            List<UserDto> userDtos = userRepository.findById(Long.parseLong(keyword), pageable)
                .stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());

            if(userDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "user_list";
            } else {
                Page<UserDto> users = new PageImpl<>(userDtos, pageable, userDtos.size());
                model.addAttribute("users", users);
                return "user_list";
            }
        } else if(type.equals("nickName")) {
            List<UserDto> userDtos = userRepository.findByNickName(keyword, pageable)
                    .stream()
                    .map(user -> new UserDto(user))
                    .collect(Collectors.toList());

            if(userDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "user_list";
            } else {
                Page<UserDto> users = new PageImpl<>(userDtos, pageable, userDtos.size());
                model.addAttribute("users", users);
                return "user_list";
            }
        } else if(type.equals("email")) {
            List<UserDto> userDtos = userRepository.findByEmail(keyword, pageable)
                    .stream()
                    .map(user -> new UserDto(user))
                    .collect(Collectors.toList());
            
            if(userDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "user_list";
            } else {
                Page<UserDto> users = new PageImpl<>(userDtos, pageable, userDtos.size());
                model.addAttribute("users", users);
                return "user_list";
            }
        } else {
            List<UserDto> userDtos = userRepository.findByPhoneNumber(keyword, pageable)
                    .stream()
                    .map(user -> new UserDto(user))
                    .collect(Collectors.toList());

            if(userDtos.size() == 0) {
                model.addAttribute("success", -1);
                return "user_list";
            } else {
                Page<UserDto> users = new PageImpl<>(userDtos, pageable, userDtos.size());
                model.addAttribute("users", users);
                return "user_list";
            }
        }
    }
}
