package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.CategoryAdd;
import com.ducks.goodsduck.admin.model.dto.CategoryEdit;
import com.ducks.goodsduck.admin.model.dto.CheckDto;
import com.ducks.goodsduck.admin.model.dto.ItemDto;
import com.ducks.goodsduck.admin.model.entity.category.*;
import com.ducks.goodsduck.admin.repository.category.*;
import com.ducks.goodsduck.admin.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final UserReportCategoryRepository userReportCategoryRepository;
    private final ItemReportCategoryRepository itemReportCategoryRepository;
    private final PostReportCategoryRepository postReportCategoryRepository;
    private final ChatReportCategoryRepository chatReportCategoryRepository;
    private final CommentReportCategoryRepository commentReportCategoryRepository;

    // FEAT : 굿즈 카테고리 리스트
    @GetMapping("/category/item")
    public String getItemCategories(Model model) {
        List<ItemCategory> categories = itemCategoryRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", "item");
        return "category_list";
    }

    // FEAT : 포스트 카테고리 리스트
    @GetMapping("/category/post")
    public String getPostCategories(Model model) {
        List<PostCategory> categories = postCategoryRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", "post");
        return "category_list";
    }

    // FEAT : 유저 신고 카테고리 리스트
    @GetMapping("/category/user-report")
    public String getUserReportCategories(Model model) {
        List<UserReportCategory> categories = userReportCategoryRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", "user-report");
        return "category_list";
    }

    // FEAT : 굿즈 신고 카테고리 리스트
    @GetMapping("/category/item-report")
    public String getItemReportCategories(Model model) {
        List<ItemReportCategory> categories = itemReportCategoryRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", "item-report");
        return "category_list";
    }

    // FEAT : 채팅 신고 카테고리 리스트
    @GetMapping("/category/chat-report")
    public String getChatReportCategories(Model model) {
        List<ChatReportCategory> categories = chatReportCategoryRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", "chat-report");
        return "category_list";
    }

    // FEAT : 포스트 신고 카테고리 리스트
    @GetMapping("/category/post-report")
    public String getPostReportCategories(Model model) {
        List<PostReportCategory> categories = postReportCategoryRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", "post-report");
        return "category_list";
    }

    // FEAT : 댓글 신고 카테고리 리스트
    @GetMapping("/category/comment-report")
    public String getCommentReportCategories(Model model) {
        List<CommentReportCategory> categories = commentReportCategoryRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", "comment-report");
        return "category_list";
    }

    // FEAT : 카테고리 추가 폼
    @GetMapping("/category/{categoryType}/add")
    public String getCategoryAddForm(@PathVariable("categoryType") String categoryType, Model model) {

        model.addAttribute("type", categoryType);

        if(categoryType.equals("item")) {
            model.addAttribute("title", "굿즈 카테고리 추가");
        } else if(categoryType.equals("post")) {
            model.addAttribute("title", "포스트 카테고리 추가");
        } else if(categoryType.equals("user-report")) {
            model.addAttribute("title", "유저 신고 카테고리 추가");
        } else if(categoryType.equals("item-report")) {
            model.addAttribute("title", "굿즈 신고 카테고리 추가");
        } else if(categoryType.equals("chat-report")) {
            model.addAttribute("title", "채팅 신고 카테고리 추가");
        } else if(categoryType.equals("post-report")) {
            model.addAttribute("title", "포스트 신고 카테고리 추가");
        } else if(categoryType.equals("comment-report")) {
            model.addAttribute("title", "댓글 신고 카테고리 추가");
        }

        return "category_add";
    }

    // FEAT : 카테고리 추가
    @PostMapping("/category/{categoryType}/add")
    public String addCategory(@PathVariable("categoryType") String categoryType, @ModelAttribute CategoryAdd categoryAdd, Model model) {

        if(categoryType.equals("item")) {
            ItemCategory itemCategory = new ItemCategory(categoryAdd.getName());
            itemCategoryRepository.save(itemCategory);
        }
        else if(categoryType.equals("post")) {
            PostCategory postCategory = new PostCategory(categoryAdd.getName());
            postCategoryRepository.save(postCategory);
        }
        else if(categoryType.equals("user-report")) {
            UserReportCategory userReportCategory = new UserReportCategory(categoryAdd.getName());
            userReportCategoryRepository.save(userReportCategory);
        }
        else if(categoryType.equals("item-report")) {
            ItemReportCategory itemReportCategory = new ItemReportCategory(categoryAdd.getName());
            itemReportCategoryRepository.save(itemReportCategory);
        }
        else if(categoryType.equals("chat-report")) {
            ChatReportCategory chatReportCategory = new ChatReportCategory(categoryAdd.getName());
            chatReportCategoryRepository.save(chatReportCategory);
        }
        else if(categoryType.equals("post-report")) {
            PostReportCategory postReportCategory = new PostReportCategory(categoryAdd.getName());
            postReportCategoryRepository.save(postReportCategory);
        }
        else if(categoryType.equals("comment-report")) {
            CommentReportCategory commentReportCategory = new CommentReportCategory(categoryAdd.getName());
            commentReportCategoryRepository.save(commentReportCategory);
        }

        model.addAttribute("type", "categoryAdd");
        model.addAttribute("message", "카테고리 추가가 완료되었습니다.");
        model.addAttribute("categoryType", categoryType);

        return "message";
    }

    // FEAT : 카테고리 수정 폼
    @GetMapping("/category/{categoryType}/edit/{categoryId}")
    public String getCategoryEditForm(@PathVariable("categoryType") String categoryType,
                                      @PathVariable("categoryId") Long categoryId, Model model) {

        Category category = categoryRepository.findById(categoryId).get();
        model.addAttribute("category", category);
        model.addAttribute("type", categoryType);

        if(categoryType.equals("item")) {
            model.addAttribute("title", "굿즈 카테고리 수정");
        } else if(categoryType.equals("post")) {
            model.addAttribute("title", "포스트 카테고리 수정");
        } else if(categoryType.equals("user-report")) {
            model.addAttribute("title", "유저 신고 카테고리 수정");
        } else if(categoryType.equals("item-report")) {
            model.addAttribute("title", "굿즈 신고 카테고리 수정");
        } else if(categoryType.equals("chat-report")) {
            model.addAttribute("title", "채팅 신고 카테고리 수정");
        } else if(categoryType.equals("post-report")) {
            model.addAttribute("title", "포스트 신고 카테고리 수정");
        } else if(categoryType.equals("comment-report")) {
            model.addAttribute("title", "댓글 신고 카테고리 수정");
        }

        return "category_edit";
    }

    // FEAT : 카테고리 수정
    @PostMapping("/category/{categoryType}/edit/{categoryId}")
    public String editCategory(@PathVariable("categoryType") String categoryType, @PathVariable("categoryId") Long categoryId,
                               @ModelAttribute CategoryEdit categoryEdit, Model model) {

        Category category = categoryRepository.findById(categoryId).get();
        category.setName(categoryEdit.getName());

        model.addAttribute("type", "categoryEdit");
        model.addAttribute("message", "카테고리 수정이 완료되었습니다.");
        model.addAttribute("categoryType", categoryType);

        return "message";
    }
}
