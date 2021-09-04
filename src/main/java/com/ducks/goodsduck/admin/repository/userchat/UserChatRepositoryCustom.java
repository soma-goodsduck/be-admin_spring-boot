package com.ducks.goodsduck.admin.repository.userchat;

import com.ducks.goodsduck.admin.model.entity.Chat;
import com.ducks.goodsduck.admin.model.entity.User;
import com.ducks.goodsduck.admin.model.entity.UserChat;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRepositoryCustom {
    List<UserChat> findAllByChatId(String chatId);
    List<Tuple> findChatAndItemByUserId(Long userId);
    User findSenderByChatIdAndUserId(String chatId, Long senderId);
    Chat findByUserIdAndItemId(Long userId, Long itemId);
    List<Tuple> findByItemIdExceptItemOwner(Long itemOwnerId, Long itemId);
    List<UserChat> findByItemId(Long itemId);
    UserChat findBySenderIdAndChatRoomId(Long senderId, String chatRoomId);
}
