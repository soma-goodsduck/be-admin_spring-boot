package com.ducks.goodsduck.admin.model.dto.idol;

import com.ducks.goodsduck.admin.model.entity.IdolMember;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class IdolMemberDto {

    private Long id;
    private String name;
    private String imageURL;
    private String groupName;
    private String groupImageURL;

    public IdolMemberDto(IdolMember idolMember) {
        this.id = idolMember.getId();
        this.name = idolMember.getName();
        this.imageURL = idolMember.getImageUrl();
        this.groupName = idolMember.getIdolGroup().getName();
        this.groupImageURL = idolMember.getIdolGroup().getImageUrl();
    }
}
