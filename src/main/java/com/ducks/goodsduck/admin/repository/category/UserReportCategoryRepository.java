package com.ducks.goodsduck.admin.repository.category;

import com.ducks.goodsduck.admin.model.entity.category.PostReportCategory;
import com.ducks.goodsduck.admin.model.entity.category.UserReportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportCategoryRepository extends JpaRepository<UserReportCategory, Long> {
}
