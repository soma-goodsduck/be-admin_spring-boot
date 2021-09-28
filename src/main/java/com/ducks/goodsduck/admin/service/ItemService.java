package com.ducks.goodsduck.admin.service;

import com.ducks.goodsduck.admin.model.entity.*;
import com.ducks.goodsduck.admin.model.entity.Image.Image;
import com.ducks.goodsduck.admin.model.entity.Image.ItemImage;
import com.ducks.goodsduck.admin.repository.ChatRepository;
import com.ducks.goodsduck.admin.repository.ItemRepository;
import com.ducks.goodsduck.admin.repository.userchat.UserChatRepository;
import com.ducks.goodsduck.admin.repository.image.ItemImageRepository;
import com.ducks.goodsduck.admin.repository.pricepropose.PriceProposeRepository;
import com.ducks.goodsduck.admin.repository.review.ReviewRepository;
import com.ducks.goodsduck.admin.repository.useritem.UserItemRepository;
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

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final PriceProposeRepository priceProposeRepository;
    private final UserItemRepository userItemRepository;
    private final UserChatRepository userChatRepository;
    private final ChatRepository chatRepository;
    private final ReviewRepository reviewRepository;

    public Long delete(Long itemId) {

        try {
            Item deleteItem = itemRepository.findById(itemId).get();

            // user's item 목록 삭제
            User user = deleteItem.getUser();
            List<Item> itemsOfUser = user.getItems();
            itemsOfUser.remove(deleteItem);

            // image 연관 삭제
            List<ItemImage> deleteImages = deleteItem.getImages();
            itemImageRepository.deleteAllInBatch(deleteImages);

            // pricePropose 연관 삭제
            List<PricePropose> deletePriceProposes = priceProposeRepository.findAllByItemIdWithAllStatus(itemId);
            priceProposeRepository.deleteAllInBatch(deletePriceProposes);

            // userChat 연관 삭제
            List<UserChat> deleteUserChats = userChatRepository.findByItemId(itemId);
            userChatRepository.deleteAllInBatch(deleteUserChats);

            // chat 삭제
            List<Chat> deleteChats = new ArrayList<>();
            for (UserChat deleteUserChat : deleteUserChats) {
                deleteChats.add(deleteUserChat.getChat());
            }
            chatRepository.deleteAllInBatch(deleteChats);

            // TODO
            // review 연관 삭제 (삭제용 데이터 0번 아이템으로 교체)
            List<Review> deleteItemOfReviews = reviewRepository.findByItemId(itemId);
            for (Review deleteItemOfReview : deleteItemOfReviews) {
                deleteItemOfReview.deleteItem();
            }

            // userItem 연관 삭제
            List<UserItem> deleteUserItems = userItemRepository.findByItemId(itemId);
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
            Item deleteItem = itemRepository.findById(itemId).get();

            // user's Item 목록 삭제
            User user = deleteItem.getUser();
            List<Item> deleteItemsOfUser = user.getItems();
            deleteItemsOfUser.remove(deleteItem);

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
