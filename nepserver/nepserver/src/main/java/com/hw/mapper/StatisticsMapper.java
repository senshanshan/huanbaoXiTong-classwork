package com.hw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hw.dto.AqiDistributeTotalStatisDto;
import com.hw.dto.AqiTrendTotalStatisDto;
import com.hw.dto.ProvinceItemTotalStatisDto;
import com.hw.dto.StatisticsPageRequestDto;
import com.hw.po.Statistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper extends BaseMapper<Statistics> {

    // 已有分页/单条查询
    int getStatisticsCount(StatisticsPageRequestDto dto);
    List<Statistics> listStatisticsPage(StatisticsPageRequestDto dto);
    Statistics getStatisticsById(Integer id);

    // 必须新增这三个方法，否则 Service 调用会报错
    List<ProvinceItemTotalStatisDto> listProvinceItemTotalStatis();
    List<AqiDistributeTotalStatisDto> listAqiDistributeTotalStatis();
    List<AqiTrendTotalStatisDto> listAqiTrendTotalStatis(List<AqiTrendTotalStatisDto> list);
}