package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.Item;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepositoryCustom {

    List<Tuple> findWithTOP5();
}
