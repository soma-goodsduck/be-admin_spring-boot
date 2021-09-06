package com.ducks.goodsduck.admin.model.entity.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("CommentReport")
public class CommentReportCategory extends Category {

    public CommentReportCategory(String name) {
        super(name);
    }
}
