package com.ducks.goodsduck.admin.service;

import com.ducks.goodsduck.admin.model.dto.idol.IdolGroupTOP5;
import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.repository.idol.IdolGroupRepository;
import com.ducks.goodsduck.admin.repository.ItemRepository;
import com.ducks.goodsduck.admin.repository.PostRepository;
import com.ducks.goodsduck.admin.repository.UserRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PostRepository postRepository;
    private final IdolGroupRepository idolGroupRepository;

    public Model home(Model model) {

        model.addAttribute("userCount", userRepository.findAll().size());
        model.addAttribute("itemCount", itemRepository.findAll().size());
        model.addAttribute("postCount", postRepository.findAll().size());
        model.addAttribute("idolGroupCount", idolGroupRepository.findAll().size());

        List<Tuple> itemTupleTOP5 = itemRepository.findWithTOP5()
                .stream()
                .sorted((tuple1, tuple2) -> tuple2.get(1, Long.class).compareTo(tuple1.get(1, Long.class)))
                .collect(Collectors.toList());

        List<Long> idolGroupCountTOP5 = itemTupleTOP5
                .stream()
                .map(tuple -> tuple.get(1, Long.class))
                .collect(Collectors.toList());

        Long idolGroupSumTOP5 = 0L;
        for (Long count : idolGroupCountTOP5) {
            idolGroupSumTOP5 += count;
        }
        final Long sum = idolGroupSumTOP5;

        List<IdolGroupTOP5> idolGroupTOP5 = itemTupleTOP5
                .stream()
                .map(tuple -> {
                    Item item = tuple.get(0, Item.class);
                    Long count = tuple.get(1, Long.class);
                    return new IdolGroupTOP5(item.getIdolMember().getIdolGroup().getName(), count, sum);
                })
                .collect(Collectors.toList());

        model.addAttribute("idolGroupTOP5", idolGroupTOP5);
        model.addAttribute("idolGroupSumTOP5", idolGroupSumTOP5);

        return model;
    }
}
