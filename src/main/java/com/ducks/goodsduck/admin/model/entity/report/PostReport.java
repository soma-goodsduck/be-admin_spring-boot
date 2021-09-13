package com.ducks.goodsduck.admin.model.entity.report;

import com.ducks.goodsduck.admin.model.entity.Image.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PostReport")
public class PostReport extends Report {

    private Long postId;
}
