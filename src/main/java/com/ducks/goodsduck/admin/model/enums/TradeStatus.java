package com.ducks.goodsduck.admin.model.enums;

public enum TradeStatus {
    BUYING("구매중"),
    SELLING("판매중"),
    RESERVING("예약중"),
    COMPLETE("거래완료"),
    REVIEW("리뷰작성");

    private String korName;

    TradeStatus(String korName) {
        this.korName = korName;
    }

    public String getKorName() {
        return korName;
    }
}