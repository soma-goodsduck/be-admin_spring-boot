package com.ducks.goodsduck.admin.repository.image;

import com.ducks.goodsduck.admin.model.entity.Image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUrl(String imageUrl);
}
