package com.ducks.goodsduck.admin.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    @Column(name = "chat_id")
    private String id;

    public Chat(String id) {
        this.id = id;
    }
}
