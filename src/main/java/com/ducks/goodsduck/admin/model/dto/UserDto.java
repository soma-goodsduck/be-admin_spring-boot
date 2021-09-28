package com.ducks.goodsduck.admin.model.dto;

import com.ducks.goodsduck.admin.model.entity.User;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class UserDto {

    private Long id;
    private String nickName;
    private String email;
    private String phoneNumber;
    private Integer level;
    private Integer reportCount;
    private String createDate;
    private String lastLoginDate;

    public UserDto(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.level = user.getLevel();
        this.reportCount = user.getReportCount();
        this.createDate = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.lastLoginDate = user.getLastLoginAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
