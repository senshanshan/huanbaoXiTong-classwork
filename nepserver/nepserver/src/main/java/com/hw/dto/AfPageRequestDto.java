package com.hw.dto;

public class AfPageRequestDto {
    //查询条件
    private Integer provinceId;
    private Integer cityId;
    private Integer estimatedGrade;
    private String afDate;
    private Integer state;
    //分页参数
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
    public Integer getEstimatedGrade() {
        return estimatedGrade;
    }
    public void setEstimatedGrade(Integer estimatedGrade) {
        this.estimatedGrade = estimatedGrade;
    }
    public String getAfDate() {
        return afDate;
    }
    public void setAfDate(String afDate) {
        this.afDate = afDate;
    }
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
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