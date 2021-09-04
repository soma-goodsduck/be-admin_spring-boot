package com.ducks.goodsduck.admin.repository.image;

import com.ducks.goodsduck.admin.model.entity.Image.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
