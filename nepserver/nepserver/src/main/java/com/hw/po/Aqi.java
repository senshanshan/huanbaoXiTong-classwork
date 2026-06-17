package com.hw.po;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("aqi")
public class Aqi {
    @TableId(value = "aqi_id",type = IdType.AUTO)
    private Integer aqiId;
    private String chineseExplain;
    private String color;
    private String aqiExplain;
    private Integer so2Min;
    private Integer so2Max;

    //get set
    public Integer getAqiId() {return aqiId;}
    public void setAqiId(Integer aqiId) {this.aqiId = aqiId;}
    public String getChineseExplain() {return chineseExplain;}
    public void setChineseExplain(String chineseExplain) {this.chineseExplain = chineseExplain;}
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}
    public String getAqiExplain() {return aqiExplain;}
    public void setAqiExplain(String aqiExplain) {this.aqiExplain = aqiExplain;}
    public Integer getSo2Min() {return so2Min;}
    public void setSo2Min(Integer so2Min) {this.so2Min = so2Min;}
    public Integer getSo2Max() {return so2Max;}
    public void setSo2Max(Integer so2Max) {this.so2Max = so2Max;}
}