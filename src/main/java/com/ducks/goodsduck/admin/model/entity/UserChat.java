package com.ducks.goodsduck.admin.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDateTime deletedAt;

    public UserChat(User user, Chat chat, Item item) {
        this.user = user;
        this.chat = chat;
        this.item = item;
    }
}
