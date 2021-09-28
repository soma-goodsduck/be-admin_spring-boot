package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.CheckDto;
import com.ducks.goodsduck.admin.model.dto.ItemDetail;
import com.ducks.goodsduck.admin.model.dto.ItemDto;
import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.repository.ItemRepository;
import com.ducks.goodsduck.admin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    // FEAT : 굿즈 검색
    @GetMapping("/item")
    public String searchByItemId(@RequestParam("search") Long itemId, Model model,
                                 @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<ItemDto> itemDtos = itemRepository.findById(itemId, pageable)
                .stream()
                .filter(item -> item.getDeletedAt() == null)
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());

        if(itemDtos.size() == 0) {
            model.addAttribute("success", -1);
            return "item_list";
        } else {
            Page<ItemDto> items = new PageImpl<>(itemDtos, pageable, itemDtos.size());
            model.addAttribute("items", items);
            return "item_list";
        }
    }

    // FEAT : 굿즈 삭제
    @GetMapping("/item/delete/{itemId}")
    @ResponseBody
    public CheckDto deleteByItemId(@PathVariable("itemId") Long itemId) {
        Long success = itemService.deleteV2(itemId);
        return new CheckDto(success);
    }

    // FEAT : 굿즈 상세보기
    @GetMapping("/item/{itemId}")
    public String showItemDetail(@PathVariable("itemId") Long itemId, Model model) {
        ItemDetail itemDetail = itemRepository.findById(itemId).map(item -> new ItemDetail(item)).get();
        model.addAttribute("item", itemDetail);
        return "item_detail";
    }
}
