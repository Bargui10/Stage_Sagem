package com.example.stage.dto;

public class FeederHistoryDTO {

    private Integer feederHistoryId;
    private String expectedPn;
    private Double price;


    public Integer getFeederHistoryId() {
        return feederHistoryId;
    }

    public void setFeederHistoryId(Integer feederHistoryId) {
        this.feederHistoryId = feederHistoryId;
    }

    public String getExpectedPn() {
        return expectedPn;
    }

    public void setExpectedPn(String expectedPn) {
        this.expectedPn = expectedPn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
