package com.ducks.goodsduck.admin.model.entity;

import com.ducks.goodsduck.admin.model.enums.SocialType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount {

    @Id @Column(name = "social_account_id")
    private String id;

    @Enumerated(EnumType.STRING)
    private SocialType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public SocialAccount(String id, SocialType type) {
        this.id = id;
        this.type = type;
    }
}
