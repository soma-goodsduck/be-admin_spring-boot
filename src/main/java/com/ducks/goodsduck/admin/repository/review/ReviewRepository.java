package com.ducks.goodsduck.admin.repository.review;

import com.ducks.goodsduck.admin.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    boolean existsByItemIdAndUserId(Long itemId, Long userId);
}
