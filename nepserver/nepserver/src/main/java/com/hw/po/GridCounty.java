package com.hw.po;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("grid_county")
public class GridCounty {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long cityId;
    private String name;
    private String code;
}