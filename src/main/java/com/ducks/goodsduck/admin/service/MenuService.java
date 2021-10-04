package com.ducks.goodsduck.admin.service;

import com.ducks.goodsduck.admin.model.dto.idol.IdolGroupStatistic;
import com.ducks.goodsduck.admin.model.dto.likeIdolGroupStatistic;
import com.ducks.goodsduck.admin.model.entity.IdolGroup;
import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.model.entity.UserIdolGroup;
import com.ducks.goodsduck.admin.repository.idol.IdolGroupRepository;
import com.ducks.goodsduck.admin.repository.ItemRepository;
import com.ducks.goodsduck.admin.repository.PostRepository;
import com.ducks.goodsduck.admin.repository.UserRepository;
import com.ducks.goodsduck.admin.util.PropertyUtil;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    String accessKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.accessKeySecretManager");
    String secretKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.secretKeySecretManager");

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PostRepository postRepository;
    private final IdolGroupRepository idolGroupRepository;

    public Model home(Model model) {

        Long userCount = userRepository.count();
        Long itemCount = itemRepository.count();
        model.addAttribute("userCount", userCount);
        model.addAttribute("itemCount", itemCount);
        model.addAttribute("postCount", postRepository.findAll().size());
        model.addAttribute("idolGroupCount", idolGroupRepository.findAll().size());

        List<IdolGroupStatistic> idolGroupStatistics = getIdolGroupStatistics(itemCount);
        List<likeIdolGroupStatistic> likeIdolGroupStatistics = getLikeIdolGroupStatistics(userCount);

        model.addAttribute("idolGroupStatistics", idolGroupStatistics);
        model.addAttribute("likeIdolGroupStatistics", likeIdolGroupStatistics);
        return model;
    }

    private List<likeIdolGroupStatistic> getLikeIdolGroupStatistics(Long userCount) {

        Map<IdolGroup, Long> map = new HashMap<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<UserIdolGroup> userIdolGroups = user.getUserIdolGroups();
            for (UserIdolGroup userIdolGroup : userIdolGroups) {
                IdolGroup idolGroup = userIdolGroup.getIdolGroup();
                map.put(idolGroup, map.get(idolGroup) == null ? 1L : map.get(idolGroup) + 1);
            }
        }

        Comparator<likeIdolGroupStatistic> compare = Comparator
                .comparing(likeIdolGroupStatistic::getCount).reversed()
                .thenComparing(likeIdolGroupStatistic::getName);

        return new LinkedList<>(map.entrySet())
                .stream()
                .map(ig -> new likeIdolGroupStatistic(ig.getKey().getName(), ig.getValue(), userCount))
                .sorted(compare)
                .collect(Collectors.toList());
    }

    private List<IdolGroupStatistic> getIdolGroupStatistics(Long itemCount) {

        Comparator<IdolGroupStatistic> compare = Comparator
                .comparing(IdolGroupStatistic::getCount).reversed()
                .thenComparing(IdolGroupStatistic::getName);

        List<IdolGroupStatistic> idolGroupStatistics = itemRepository.findAllWithItemCount()
                .stream()
                .map(tuple -> {
                    Item item = tuple.get(0, Item.class);
                    Long itemEachCount = tuple.get(1, Long.class);
                    return new IdolGroupStatistic(item.getIdolMember().getIdolGroup().getName(), itemEachCount, itemCount);
                })
                .sorted(compare)
                .collect(Collectors.toList());

        return idolGroupStatistics;
    }
}
