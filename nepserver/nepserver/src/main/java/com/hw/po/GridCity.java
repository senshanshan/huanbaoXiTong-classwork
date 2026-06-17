package com.hw.po;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("grid_city")
public class GridCity {
    @TableId(value = "city_id",type = IdType.AUTO)
    private Integer cityId;
    private String cityName;
    private Integer provinceId;

    //get set
    public Integer getCityId() {return cityId;}
    public void setCityId(Integer cityId) {this.cityId = cityId;}
    public String getCityName() {return cityName;}
    public void setCityName(String cityName) {this.cityName = cityName;}
    public Integer getProvinceId() {return provinceId;}
    public void setProvinceId(Integer provinceId) {this.provinceId = provinceId;}
}