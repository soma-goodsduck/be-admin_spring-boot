package com.ducks.goodsduck.admin.repository.pricepropose;

import com.ducks.goodsduck.admin.model.entity.PricePropose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceProposeRepository extends JpaRepository<PricePropose, Long>, PriceProposeRepositoryCustom {
}
