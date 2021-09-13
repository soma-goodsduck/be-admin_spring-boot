package com.ducks.goodsduck.admin.model.entity.report;

import com.ducks.goodsduck.admin.model.entity.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ItemReport")
public class ItemReport extends Report {

    private Long itemId;
}
