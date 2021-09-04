package com.ducks.goodsduck.admin.model.dto.idol;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class IdolMemberEdit {

    private String name;
    private String groupName;
    private MultipartFile multipartFile;
}
