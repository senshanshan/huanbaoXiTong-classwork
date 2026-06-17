package com.hw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hw.po.Aqi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface AqiMapper extends BaseMapper<Aqi> {
    //按城市查空气质量列表
    @Select("select * from aqi where city_code=#{cityCode} order by record_time desc")
    List<Aqi> selectByCity(String cityCode);
}