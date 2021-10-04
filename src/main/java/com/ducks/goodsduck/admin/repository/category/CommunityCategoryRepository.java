package com.ducks.goodsduck.admin.repository.category;

import com.ducks.goodsduck.admin.model.entity.category.CommentReportCategory;
import com.ducks.goodsduck.admin.model.entity.category.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Long> {
}
