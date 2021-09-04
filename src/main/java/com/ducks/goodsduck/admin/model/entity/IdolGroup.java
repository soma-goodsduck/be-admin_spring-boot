package com.ducks.goodsduck.admin.model.entity;

import com.ducks.goodsduck.admin.model.dto.idol.IdolGroupAdd;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdolGroup {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idol_group_id")
    private Long id;
    private String name;
    private String korName;
    private String engName;
    private Long votedCount;
    private String imageUrl;

    public IdolGroup(IdolGroupAdd idolGroupAdd) {
        this.name = idolGroupAdd.getName();
        this.korName = idolGroupAdd.getKorName();
        this.engName = idolGroupAdd.getEngName();
        this.votedCount = 0L;
    }
}
