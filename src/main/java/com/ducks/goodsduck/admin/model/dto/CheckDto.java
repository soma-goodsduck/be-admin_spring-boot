package com.ducks.goodsduck.admin.model.dto;

import lombok.Data;

@Data
public class CheckDto {

    private Long success;

    public CheckDto(Long success) {
        this.success = success;
    }
}
