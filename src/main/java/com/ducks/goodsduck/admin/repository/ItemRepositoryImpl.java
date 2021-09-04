package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.model.enums.TradeStatus;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ducks.goodsduck.admin.model.entity.QItem.*;

@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tuple> findWithTOP5() {
        return queryFactory
                .select(item, item.count())
                .from(item)
                .where(item.tradeStatus.eq(TradeStatus.COMPLETE))
                .groupBy(item.idolMember.idolGroup)
                .limit(5)
                .fetch();
    }
}
