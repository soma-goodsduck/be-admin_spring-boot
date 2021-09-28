package com.ducks.goodsduck.admin.model.dto;

import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.model.enums.TradeStatus;
import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private Long price;
    private TradeStatus tradeStatus;
    private String idolGroup;
    private String imageURL;
    private String nickName;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.tradeStatus =  item.getTradeStatus();
        this.idolGroup = item.getIdolMember().getIdolGroup().getName();
        this.imageURL = item.getImages().get(0).getUrl();
        this.nickName = item.getUser().getNickName();
    }
}
