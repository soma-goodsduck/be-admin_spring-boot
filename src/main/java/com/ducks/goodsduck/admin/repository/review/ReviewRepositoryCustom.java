package com.ducks.goodsduck.admin.repository.review;

import com.ducks.goodsduck.admin.model.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepositoryCustom {
    boolean existsByItemIdAndSenderIdAndReceiverId(Long itemId, Long senderId, Long receiverId);
    Long countBySenderId(Long senderId);
    List<Review> findByUserId(Long userId);
    List<Review> findByReveiverId(Long receiverId);
    Long countByReveiverId(Long receiverId);
    List<Review> findByItemId(Long itemId);
    Review findByReveiverIdAndItemId(Long receiverId, Long itemId);
}