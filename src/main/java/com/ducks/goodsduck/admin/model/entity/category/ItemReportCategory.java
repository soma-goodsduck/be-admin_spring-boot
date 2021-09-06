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
@DiscriminatorValue("ItemReport")
public class ItemReportCategory extends Category {

    public ItemReportCategory(String name) {
        super(name);
    }
}
