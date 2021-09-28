package com.ducks.goodsduck.admin.model.entity;

import com.ducks.goodsduck.admin.model.enums.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String bcryptId;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private Integer level;
    private Integer exp;
    private Integer reportCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserIdolGroup> userIdolGroups = new ArrayList<>();

    public void levelUp() {
        this.level++;
        this.exp -= 100;
    }
}
