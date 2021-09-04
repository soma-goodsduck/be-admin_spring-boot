package com.ducks.goodsduck.admin.model.dto.idol;

import com.ducks.goodsduck.admin.model.entity.IdolMember;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@NoArgsConstructor
public class IdolMemberAdd {

    private List<String> name;
    private List<String> groupName;
    private List<MultipartFile> multipartFile;
}
