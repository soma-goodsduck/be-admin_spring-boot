package com.ducks.goodsduck.admin.model.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostUpdateRequest {

    private String title;
    private String content;
    private List<String> imageUrls;
}
