package com.ducks.goodsduck.admin.model.entity;

import com.ducks.goodsduck.admin.model.entity.Image.Post;
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

    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

//    public User(String nickName, String email, String phoneNumber) {
//        this.nickName = nickName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
////        this.createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.of("Asia/Seoul"));
//        this.createdAt = LocalDateTime.now();
////        this.lastLoginAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.of("Asia/Seoul"));
//        this.lastLoginAt = LocalDateTime.now();
//        this.role = UserRole.USER;
//        this.level = 1;
//        this.exp = 0;
//        this.bcryptId = createBcryptId();
//    }

//    public String createBcryptId() {
//        Random random = new Random();
//        int number = random.nextInt(26) + 65;
//
//        String tempBcryptId = BCrypt.hashpw(this.phoneNumber, BCrypt.gensalt(5));
//        return tempBcryptId.replace('/', (char)number);
//    }

    public void addSocialAccount(SocialAccount socialAccount) {
        socialAccount.setUser(this);
    }

    public void addUserIdolGroup(UserIdolGroup userIdolGroup) {
        userIdolGroup.setUser(this);
        userIdolGroups.add(userIdolGroup);
    }

    public void updateLastLoginAt() {
//        this.lastLoginAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.of("Asia/Seoul"));
        this.lastLoginAt = LocalDateTime.now();
    }

    public void gainExp(int exp) {
        this.exp += exp;
        if(this.exp >= 100) {
            levelUp();
        }
    }

    public void levelUp() {
        this.level++;
        this.exp -= 100;
    }
}