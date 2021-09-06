package com.ducks.goodsduck.admin.repository.category;

import com.ducks.goodsduck.admin.model.entity.category.ItemCategory;
import com.ducks.goodsduck.admin.model.entity.category.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
