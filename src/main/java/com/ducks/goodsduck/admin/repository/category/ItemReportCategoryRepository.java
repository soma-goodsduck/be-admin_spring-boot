package com.ducks.goodsduck.admin.repository.category;

import com.ducks.goodsduck.admin.model.entity.category.ItemReportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReportCategoryRepository extends JpaRepository<ItemReportCategory, Long> {
}
