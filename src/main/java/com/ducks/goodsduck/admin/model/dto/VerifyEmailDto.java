package com.ducks.goodsduck.admin.model.dto;

import lombok.Data;

@Data
public class VerifyEmailDto {

    private String email;
    private String code;
}
