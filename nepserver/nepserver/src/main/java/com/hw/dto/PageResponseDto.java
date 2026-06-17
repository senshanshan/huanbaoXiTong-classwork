package com.hw.dto;
import java.util.List;

public class PageResponseDto<T> {
    private Integer totalRow;
    private Integer totalPageNum;
    private Integer preNum;
    private Integer nextNum;
    private Integer pageNum;
    private Integer maxPageNum;
    private Integer beginNum;
    private List<T> list;

    public Integer getTotalRow() {
        return totalRow;
    }
    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }
    public Integer getTotalPageNum() {
        return totalPageNum;
    }
    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
    public Integer getPreNum() {
        return preNum;
    }
    public void setPreNum(Integer preNum) {
        this.preNum = preNum;
    }
    public Integer getNextNum() {
        return nextNum;
    }
    public void setNextNum(Integer nextNum) {
        this.nextNum = nextNum;
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
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
}