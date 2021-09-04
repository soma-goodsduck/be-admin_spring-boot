package com.ducks.goodsduck.admin.repository.image;

import com.ducks.goodsduck.admin.model.entity.Image.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
