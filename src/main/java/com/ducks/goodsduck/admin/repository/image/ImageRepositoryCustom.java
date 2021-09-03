package com.ducks.goodsduck.admin.repository.image;

import com.ducks.goodsduck.admin.model.entity.Image.Image;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepositoryCustom {

    List<Image> findByImageUrls(List<String> imageUrls);
}
