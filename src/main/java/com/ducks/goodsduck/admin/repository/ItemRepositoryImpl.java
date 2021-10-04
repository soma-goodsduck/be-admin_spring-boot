package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.Item;
import com.ducks.goodsduck.admin.model.enums.TradeStatus;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public List<Tuple> findAllWithItemCount() {
        return queryFactory
                .select(item, item.count())
                .from(item)
                .groupBy(item.idolMember.idolGroup)
                .fetch();
    }

    @Override
    public Page<Item> findAllWithPageable(Pageable pageable) {
        JPAQuery<Item> query = queryFactory
                .select(item)
                .from(item)
                .where(item.deletedAt.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Long totalCount = query.fetchCount();
        List<Item> items = query.fetch();
        return new PageImpl<>(items, pageable, totalCount);
    }

//    @Override
//    public Page<Driver> searchAll(Pageable pageable){
//        QueryResults<Driver> result = queryFactory
//                .selectFrom(driver)
//                .where(eqCity(search.getCityCode()),
//                        eqStatus(search.getStatus())
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//        return new PageImpl<>(result.getResults(),pageable,result.getTotal());
//    }
}
