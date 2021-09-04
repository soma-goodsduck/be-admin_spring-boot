package com.ducks.goodsduck.admin.repository.useritem;

import com.ducks.goodsduck.admin.model.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long>, UserItemRepositoryCustom {
    Long countByUserId(Long userId);
}
