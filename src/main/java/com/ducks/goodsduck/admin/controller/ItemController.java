package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.CheckDto;
import com.ducks.goodsduck.admin.model.dto.ItemDto;
import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.repository.ItemRepository;
import com.ducks.goodsduck.admin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
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

    // FEAT : 상품 검색
    @GetMapping("/item")
    public String searchByItemId(@RequestParam("search") Long itemId, Model model) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(!item.isPresent()) {
            model.addAttribute("success", -1);
            return "item_list";
        } else {
            model.addAttribute("items", new ItemDto(item.get()));
            return "item_list";
        }
    }

    // FEAT : 상품 삭제
    @GetMapping("/item/delete/{itemId}")
    @ResponseBody
    public CheckDto deleteByItemId(@PathVariable("itemId") Long itemId) {
        Long success = itemService.delete(itemId);
        List<ItemDto> items = itemRepository.findAll()
                .stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
        return new CheckDto(success);
    }
}
