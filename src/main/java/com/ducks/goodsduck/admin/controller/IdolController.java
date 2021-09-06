package com.ducks.goodsduck.admin.controller;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.ducks.goodsduck.admin.model.dto.idol.*;
import com.ducks.goodsduck.admin.model.entity.IdolGroup;
import com.ducks.goodsduck.admin.model.entity.IdolMember;
import com.ducks.goodsduck.admin.model.entity.Image.Image;
import com.ducks.goodsduck.admin.repository.idol.IdolGroupRepository;
import com.ducks.goodsduck.admin.repository.idol.IdolMemberRepository;
import com.ducks.goodsduck.admin.service.IdolGroupService;
import com.ducks.goodsduck.admin.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IdolController {

    private final IdolGroupService idolGroupService;
    private final ImageUploadService imageUploadService;
    private final IdolGroupRepository idolGroupRepository;
    private final IdolMemberRepository idolMemberRepository;

    // FEAT : 아이돌 그룹 추가 폼
    @GetMapping("/idol-group/add")
    public String getIdolGroupAddForm() {
        return "idolGroup_add";
    }

    // FEAT : 아이돌 그룹 추가
    @PostMapping("/idol-group/add")
    public String addIdolGroup(@ModelAttribute IdolGroupAdd idolGroupAdd, Model model) throws ImageProcessingException, IOException, MetadataException {

        Image image = imageUploadService.uploadImage(idolGroupAdd.getMultipartFile());

        IdolGroup idolGroup = new IdolGroup(idolGroupAdd);
        idolGroup.setImageUrl(image.getUrl());
        idolGroupRepository.save(idolGroup);

        model.addAttribute("type", "idolGroupAdd");
        model.addAttribute("message", "아이돌 그룹 추가가 완료되었습니다.");

        return "message";
    }

    // FEAT : 아이돌 그룹 수정 폼
    @GetMapping("/idol-group/edit/{idolGroupId}")
    public String getIdolGroupEditForm(@PathVariable("idolGroupId") Long idolGroupId, Model model) throws ImageProcessingException, IOException, MetadataException {
        IdolGroup idolGroup = idolGroupRepository.findById(idolGroupId).get();
        model.addAttribute("idolGroup", new IdolGroupDto(idolGroup));
        return "idolGroup_edit";
    }

    // FEAT : 아이돌 그룹 수정
    @PostMapping("/idol-group/edit/{idolGroupId}")
    public String editIdolGroup(@PathVariable("idolGroupId") Long idolGroupId,
                                @ModelAttribute IdolGroupEdit idolGroupEdit, Model model) throws ImageProcessingException, IOException, MetadataException {

        Image image = imageUploadService.uploadImage(idolGroupEdit.getMultipartFile());

        IdolGroup idolGroup = idolGroupRepository.findById(idolGroupId).get();
        idolGroup.setName(idolGroupEdit.getName());
        idolGroup.setKorName(idolGroupEdit.getKorName());
        idolGroup.setEngName(idolGroupEdit.getEngName());

        if(image != null) {
            idolGroup.setImageUrl(image.getUrl());
        }

        model.addAttribute("type", "idolGroupEdit");
        model.addAttribute("message", "아이돌 그룹 수정이 완료되었습니다.");

        return "message";
    }

    // FEAT : 아이돌 그룹 선택 폼
    @GetMapping("/idol-group/select")
    public String getIdolGroupSelectForm(Model model) {
        List<IdolGroupDto> idolGroups = idolGroupRepository.findAll()
                .stream()
                .map(idolGroup -> new IdolGroupDto(idolGroup))
                .collect(Collectors.toList());
        model.addAttribute("idolGroups", idolGroups);
        return "idolGroup_select";
    }

    // FEAT : 아이돌 멤버 추가
    @GetMapping("/idol-member/add/{idolGroupId}")
    public String getIdolMemberAddForm(@PathVariable("idolGroupId") Long idolGroupId, Model model) {
        IdolGroup idolGroup = idolGroupRepository.findById(idolGroupId).get();
        model.addAttribute("idolGroup", new IdolGroupDto(idolGroup));
        return "idolMember_add";
    }

    // FEAT : 아이돌 멤버 추가
    @PostMapping("/idol-member/add/{idolGroupId}")
    public String addIdolMember(@PathVariable("idolGroupId") Long idolGroupId,
                                @ModelAttribute IdolMemberAdd idolMemberAdd, Model model) throws ImageProcessingException, IOException, MetadataException {

        int size = idolMemberAdd.getName().size();
        IdolGroup idolGroup = idolGroupRepository.findById(idolGroupId).get();

        for(int i = 0; i < size; i++) {
            IdolMember idolMember = new IdolMember(idolMemberAdd.getName().get(i));
            idolMember.setIdolGroup(idolGroup);

            Image image = imageUploadService.uploadImage(idolMemberAdd.getMultipartFile().get(i));
            idolMember.setImageUrl(image.getUrl());

            idolMemberRepository.save(idolMember);
        }

        model.addAttribute("type", "idolMemberAdd");
        model.addAttribute("message", "아이돌 멤버 추가가 완료되었습니다.");

        return "message";
    }

    // FEAT : 아이돌 멤버 수정 폼
    @GetMapping("/idol-member/edit/{idolMemberId}")
    public String getIdolMemberEditForm(@PathVariable("idolMemberId") Long idolMemberId, Model model) throws ImageProcessingException, IOException, MetadataException {
        IdolMember idolMember = idolMemberRepository.findById(idolMemberId).get();
        model.addAttribute("idolMember", new IdolMemberDto(idolMember));
        return "idolMember_edit";
    }

    // FEAT : 아이돌 멤버 수정
    @PostMapping("/idol-member/edit/{idolMemberId}")
    public String editIdolMember(@PathVariable("idolMemberId") Long idolMemberId,
                                 @ModelAttribute IdolMemberEdit idolMemberEdit, Model model) throws ImageProcessingException, IOException, MetadataException {

        Image image = imageUploadService.uploadImage(idolMemberEdit.getMultipartFile());

        IdolMember idolMember = idolMemberRepository.findById(idolMemberId).get();
        idolMember.setName(idolMemberEdit.getName());

        if(image != null) {
            idolMember.setImageUrl(image.getUrl());
        }

        model.addAttribute("type", "idolMemberEdit");
        model.addAttribute("message", "아이돌 멤버 수정이 완료되었습니다.");

        return "message";
    }
}
