package com.ducks.goodsduck.admin.repository.report;

import com.ducks.goodsduck.admin.model.entity.report.Report;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ducks.goodsduck.admin.model.entity.report.QReport.report;

@Repository
public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ReportRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Report> findAllWithPageable(Pageable pageable) {
        JPAQuery<Report> query = queryFactory
                .select(report)
                .from(report)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Long totalCount = query.fetchCount();
        List<Report> reports = query.fetch();
        return new PageImpl<>(reports, pageable, totalCount);
    }
}
