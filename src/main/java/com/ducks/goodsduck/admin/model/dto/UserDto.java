package com.ducks.goodsduck.admin.model.dto;

import com.ducks.goodsduck.admin.model.entity.User;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String nickName;

    public UserDto(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
    }
}
