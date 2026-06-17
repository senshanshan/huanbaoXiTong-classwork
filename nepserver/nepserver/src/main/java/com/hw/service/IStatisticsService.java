package com.hw.service;

import com.hw.dto.*;
import com.hw.po.Statistics;
import java.util.List;

public interface IStatisticsService {
    // 原有方法（你项目里已有的，保留即可）
    int saveStatistics(Statistics statistics);
    PageResponseDto<Statistics> listStatisticsPage(StatisticsPageRequestDto dto);
    Statistics getStatisticsById(Integer id);
    List<ProvinceItemTotalStatisDto> listProvinceItemTotalStatis();
    List<AqiDistributeTotalStatisDto> listAqiDistributeTotalStatis();
    List<AqiTrendTotalStatisDto> listAqiTrendTotalStatis();
    Long getAqiCount();
    Long getAqiGoodCount();

    // 新增这两个，解决Override报错
    String getProvinceCoverage();
    String getCityCoverage();
}