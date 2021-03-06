package com.ducks.goodsduck.admin.model.entity;

import com.ducks.goodsduck.admin.model.dto.idol.IdolMemberAdd;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdolMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idol_member_id")
    private Long id;
    private String name;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idol_group_id")
    private IdolGroup idolGroup;

    public IdolMember(String name) {
        this.name = name;
    }
}
