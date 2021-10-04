package com.ducks.goodsduck.admin.service;

import com.ducks.goodsduck.admin.model.entity.*;
import com.ducks.goodsduck.admin.model.entity.Image.Image;
import com.ducks.goodsduck.admin.model.entity.Image.ItemImage;
import com.ducks.goodsduck.admin.model.enums.ImageType;
import com.ducks.goodsduck.admin.repository.ChatRepository;
import com.ducks.goodsduck.admin.repository.ItemRepository;
import com.ducks.goodsduck.admin.repository.PostRepository;
import com.ducks.goodsduck.admin.repository.UserRepository;
import com.ducks.goodsduck.admin.repository.image.ImageRepository;
import com.ducks.goodsduck.admin.repository.image.PostImageRepository;
import com.ducks.goodsduck.admin.repository.pricepropose.PriceProposeRepositoryCustom;
import com.ducks.goodsduck.admin.repository.review.ReviewRepositoryCustom;
import com.ducks.goodsduck.admin.repository.userchat.UserChatRepository;
import com.ducks.goodsduck.admin.repository.image.ItemImageRepository;
import com.ducks.goodsduck.admin.repository.pricepropose.PriceProposeRepository;
import com.ducks.goodsduck.admin.repository.review.ReviewRepository;
import com.ducks.goodsduck.admin.repository.userchat.UserChatRepositoryCustom;
import com.ducks.goodsduck.admin.repository.useritem.UserItemRepository;
import com.ducks.goodsduck.admin.repository.useritem.UserItemRepositoryCustom;
import com.ducks.goodsduck.admin.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemService {

    String accessKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.accessKeySecretManager");
    String secretKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.secretKeySecretManager");

    private final ItemRepository itemRepository;
    private final UserChatRepository userChatRepository;
    private final UserItemRepository userItemRepository;
    private final PriceProposeRepository priceProposeRepository;
    private final ItemImageRepository itemImageRepository;
    private final ChatRepository chatRepository;
    private final ReviewRepository reviewRepository;

    private final ImageUploadService imageUploadService;

    public Long delete(Long itemId) {

        try {
            Item deleteItem = itemRepository.findById(itemId).get();

            // image 연관 삭제
            List<ItemImage> deleteImages = deleteItem.getImages();
            for (ItemImage deleteImage : deleteImages) {
                imageUploadService.deleteImage(deleteImage, ImageType.ITEM);
            }
            itemImageRepository.deleteAllInBatch(deleteImages);

            // pricePropose 연관 삭제
            List<PricePropose> deletePriceProposes = priceProposeRepository.findAllByItemIdWithAllStatus(deleteItem.getId());
            priceProposeRepository.deleteAllInBatch(deletePriceProposes);

            // userChat 연관 삭제
            List<UserChat> deleteUserChats = userChatRepository.findByItemId(deleteItem.getId());
            userChatRepository.deleteAllInBatch(deleteUserChats);

            // chat 삭제
            List<Chat> deleteChats = new ArrayList<>();
            for (UserChat deleteUserChat : deleteUserChats) {
                deleteChats.add(deleteUserChat.getChat());
            }
            chatRepository.deleteAllInBatch(deleteChats);

            // review 연관 삭제
            List<Review> deleteItemOfReviews = reviewRepository.findByItemId(deleteItem.getId());
            for (Review deleteItemOfReview : deleteItemOfReviews) {
                deleteItemOfReview.setItem(null);
            }

            // userItem 연관 삭제
            List<UserItem> deleteUserItems = userItemRepository.findByItemId(deleteItem.getId());
            userItemRepository.deleteAllInBatch(deleteUserItems);

            // item 삭제
            itemRepository.delete(deleteItem);

            return 1L;
        } catch (Exception e) {
            return -1L;
        }
    }

    public Long deleteV2(Long itemId) {

        try {
            Item deleteItem = itemRepository.findById(itemId).orElseGet(null);
            if(deleteItem == null) {
                return -1L;
            }

            // image 연관 삭제
            List<ItemImage> deleteItemImages = deleteItem.getImages();
            for (Image deleteImage : deleteItemImages) {
                deleteImage.setDeletedAt(LocalDateTime.now());
            }

            // pricePropose 연관 삭제
            List<PricePropose> deletePriceProposes = priceProposeRepository.findAllByItemIdWithAllStatus(itemId);
            for (PricePropose deletePricePropose : deletePriceProposes) {
                deletePricePropose.setDeletedAt(LocalDateTime.now());
            }

            // userChat, chat 연관 삭제
            List<UserChat> deleteUserChats = userChatRepository.findByItemId(itemId);
            for (UserChat deleteUserChat : deleteUserChats) {
                deleteUserChat.setDeletedAt(LocalDateTime.now());
                deleteUserChat.getChat().setDeletedAt(LocalDateTime.now());
            }

            // userItem 연관 삭제
            List<UserItem> deleteUserItems = userItemRepository.findByItemId(itemId);
            for (UserItem deleteUserItem : deleteUserItems) {
                deleteUserItem.setDeletedAt(LocalDateTime.now());
            }

            // Item 삭제
            deleteItem.setDeletedAt(LocalDateTime.now());

            return 1L;
        } catch (Exception e) {
            return -1L;
        }
    }
}
