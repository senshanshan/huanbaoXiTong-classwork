package com.hw.dto;

public class StatisticsPageRequestDto {
    private Integer provinceId;
    private Integer cityId;
    private String confirmDate;
    private Integer pageNum;
    private Integer maxPageNum;
    private Integer beginNum;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getMaxPageNum() {
        return maxPageNum;
    }

    public void setMaxPageNum(Integer maxPageNum) {
        this.maxPageNum = maxPageNum;
    }

    public Integer getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(Integer beginNum) {
        this.beginNum = beginNum;
    }
}