package com.ducks.goodsduck.admin.model.dto;

import lombok.Data;

@Data
public class CategoryDto {

    private String type;
    private String name;

    public CategoryDto(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
