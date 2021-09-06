package com.ducks.goodsduck.admin.repository.category;

import com.ducks.goodsduck.admin.model.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
