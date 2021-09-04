package com.ducks.goodsduck.admin.model.dto.post;

import com.ducks.goodsduck.admin.model.entity.Image.PostImage;
import lombok.Data;

@Data
public class PostDetailResponseImage {

    private String url;

    public PostDetailResponseImage(PostImage image) {
        this.url = image.getUrl();
    }
}
