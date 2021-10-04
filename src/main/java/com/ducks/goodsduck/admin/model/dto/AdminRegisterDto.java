package com.ducks.goodsduck.admin.model.dto;

import lombok.Data;

@Data
public class AdminRegisterDto {

    private String email;
    private String code;
    private String password;
}
