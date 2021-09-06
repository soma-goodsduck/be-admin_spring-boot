package com.ducks.goodsduck.admin.model.dto;

import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.model.enums.TradeStatus;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemDetail {

    private Long id;
    private String name;
    private Long price;
    private TradeStatus tradeStatus;
    private String idolGroup;
    private String description;
    private List<String> imageURLs;

    public ItemDetail(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.tradeStatus =  item.getTradeStatus();
        this.idolGroup = item.getIdolMember().getIdolGroup().getName();
        this.description = item.getDescription();
        this.imageURLs = item.getImages()
                .stream()
                .map(itemImage -> itemImage.getUrl())
                .collect(Collectors.toList());
    }
}
