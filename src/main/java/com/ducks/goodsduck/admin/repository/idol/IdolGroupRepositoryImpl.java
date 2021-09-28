package com.ducks.goodsduck.admin.repository.idol;

import com.ducks.goodsduck.admin.model.entity.IdolGroup;
import com.ducks.goodsduck.admin.model.entity.QIdolGroup;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ducks.goodsduck.admin.model.entity.QIdolGroup.idolGroup;

@Repository
public class IdolGroupRepositoryImpl implements IdolGroupRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public IdolGroupRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<IdolGroup> findAllByName(String name) {

        String[] arr = name.split(" ");
        String newName = "";
        for (String s : arr) {
            newName += s;
        }

        StringTemplate kor = Expressions.stringTemplate("REPLACE({0},' ','')", idolGroup.korName);
        StringTemplate eng = Expressions.stringTemplate("REPLACE({0},' ','')", idolGroup.engName);

        return queryFactory.select(idolGroup)
                .from(idolGroup)
                .where(kor.contains(newName).or(eng.contains(newName)))
                .fetch();
    }
}

