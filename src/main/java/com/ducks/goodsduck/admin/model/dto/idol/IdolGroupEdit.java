package com.ducks.goodsduck.admin.model.dto.idol;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class IdolGroupEdit {

    private String name;
    private String engName;
    private String korName;
    private MultipartFile multipartFile;
}
