package com.ducks.goodsduck.admin.model.dto.post;

import com.ducks.goodsduck.admin.model.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUploadRequest {

    private String title;
    private String content;
    private Long idolGroupId;
    private PostType postType;
}
