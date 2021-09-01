package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long>, UserChatRepositoryCustom {
}
