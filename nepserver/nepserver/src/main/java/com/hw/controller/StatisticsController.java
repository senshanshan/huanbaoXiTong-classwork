package com.hw.controller;

import com.hw.dto.*;
import com.hw.po.Statistics;
import com.hw.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private IStatisticsService statisticsService;

    // 新增统计数据（POST）
    @PostMapping("/saveStatistics")
    public int saveStatistics(@RequestBody Statistics statistics) {
        return statisticsService.saveStatistics(statistics);
    }

    // 分页查询（POST，全小写路径）
    @PostMapping("/listStatisticsPage")
    public PageResponseDto<Statistics> listStatisticsPage(@RequestBody StatisticsPageRequestDto dto) {
        return statisticsService.listStatisticsPage(dto);
    }

    // 根据ID查询单条（POST）
    @PostMapping("/getStatisticsById")
    public Statistics getStatisticsById(@RequestBody Statistics statistics) {
        return statisticsService.getStatisticsById(statistics.getId());
    }

    // 省份分项统计（POST）
    @PostMapping("/listProvinceItemTotalStatis")
    public List<ProvinceItemTotalStatisDto> listProvinceItemTotalStatis() {
        return statisticsService.listProvinceItemTotalStatis();
    }

    // AQI等级分布统计（POST）
    @PostMapping("/listAqiDistributeTotalStatis")
    public List<AqiDistributeTotalStatisDto> listAqiDistributeTotalStatis() {
        return statisticsService.listAqiDistributeTotalStatis();
    }

    // 月度AQI趋势统计（POST）
    @PostMapping("/listAqiTrendTotalStatis")
    public List<AqiTrendTotalStatisDto> listAqiTrendTotalStatis() {
        return statisticsService.listAqiTrendTotalStatis();
    }

    // 获取总条数（POST）
    @PostMapping("/getAqiCount")
    public Long getAqiCount() {
        return statisticsService.getAqiCount();
    }

    // 获取优良条数（POST）
    @PostMapping("/getAqiGoodCount")
    public Long getAqiGoodCount() {
        return statisticsService.getAqiGoodCount();
    }

    // 省份覆盖率（POST）
    @PostMapping("/getProvinceCoverage")
    public String getProvinceCoverage() {
        return statisticsService.getProvinceCoverage();
    }

    // 城市覆盖率（POST）
    @PostMapping("/getCityCoverage")
    public String getCityCoverage() {
        return statisticsService.getCityCoverage();
    }
}