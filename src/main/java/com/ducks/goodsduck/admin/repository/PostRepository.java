package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
