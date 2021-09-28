package com.ducks.goodsduck.admin.model.dto;

import lombok.Data;

@Data
public class likeIdolGroupStatistic {

    private String name;
    private Long count;
    private Long ratio;

    public likeIdolGroupStatistic(String name, Long count, Long sum) {
        this.name = name;
        this.count = count;
        this.ratio = Math.round(((double)count / sum) * 100);
    }
}
