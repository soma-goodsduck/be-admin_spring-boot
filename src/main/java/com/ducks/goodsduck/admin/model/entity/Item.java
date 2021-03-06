package com.ducks.goodsduck.admin.model.entity;

import com.ducks.goodsduck.admin.model.entity.Image.Image;
import com.ducks.goodsduck.admin.model.entity.Image.ItemImage;
import com.ducks.goodsduck.admin.model.entity.category.ItemCategory;
import com.ducks.goodsduck.admin.model.enums.GradeStatus;
import com.ducks.goodsduck.admin.model.enums.TradeStatus;
import com.ducks.goodsduck.admin.model.enums.TradeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name;
    private Long price;
    private String description;
    private Integer views;
    private Integer likesItemCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    @Enumerated(EnumType.STRING)
    private GradeStatus gradeStatus;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idol_member_id")
    private IdolMember idolMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id")
    private ItemCategory itemCategory;
}
