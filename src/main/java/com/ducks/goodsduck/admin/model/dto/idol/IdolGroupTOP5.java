package com.ducks.goodsduck.admin.model.dto.idol;

import lombok.Data;

@Data
public class IdolGroupTOP5 {

    String name;
    Long count;
    Long ratio;

    public IdolGroupTOP5(String name, Long count, Long sum) {
        this.name = name;
        this.count = count;
        this.ratio = Math.round(((double)count / sum) * 100);
    }
}
