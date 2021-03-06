package com.ducks.goodsduck.admin.repository.pricepropose;

import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.model.entity.PricePropose;
import com.ducks.goodsduck.admin.model.enums.PriceProposeStatus;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceProposeRepositoryCustom {
    PricePropose findByUserIdAndItemId(Long userId, Long itemId);
    List<PricePropose> findAllByItemId(Long itemId);

    List<Tuple> findByItems(List<Item> items);
    List<Tuple> findByItemId(Long itemId);
    List<Tuple> findByUserId(Long userId);
    Long updatePrice(Long userId, Long priceProposeId, int price);
    Long updateStatus(Long priceProposeId, PriceProposeStatus status);
    Long countSuggestedInItems(List<Item> itemsByUserId);

    List<PricePropose> findAllByItemIdWithAllStatus(Long itemId);

    PricePropose findByUserIdAndItemIdForChat(Long userId, Long itemId);
}
