package com.hw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hw.dto.AqiDistributeTotalStatisDto;
import com.hw.dto.AqiTrendTotalStatisDto;
import com.hw.dto.PageResponseDto;
import com.hw.dto.ProvinceItemTotalStatisDto;
import com.hw.dto.StatisticsPageRequestDto;
import com.hw.mapper.GridCityMapper;
import com.hw.mapper.GridProvinceMapper;
import com.hw.mapper.StatisticsMapper;
import com.hw.po.Statistics;
import com.hw.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Value("${global-data.province-number:34}")
    private Integer provinceNumber;

    @Value("${global-data.city-number:300}")
    private Integer cityNumber;

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private GridProvinceMapper gridProvinceMapper;

    @Autowired
    private GridCityMapper gridCityMapper;

    @Override
    public int saveStatistics(Statistics statistics) {
        return statisticsMapper.insert(statistics);
    }

    @Override
    public PageResponseDto<Statistics> listStatisticsPage(StatisticsPageRequestDto dto) {
        PageResponseDto<Statistics> response = new PageResponseDto<>();
        int pageNum = dto.getPageNum() == null || dto.getPageNum() < 1 ? 1 : dto.getPageNum();
        int maxPageNum = dto.getMaxPageNum() == null || dto.getMaxPageNum() < 1 ? 8 : dto.getMaxPageNum();
        dto.setPageNum(pageNum);
        dto.setMaxPageNum(maxPageNum);

        int totalRow = statisticsMapper.getStatisticsCount(dto);
        response.setTotalRow(totalRow);
        response.setPageNum(pageNum);
        response.setMaxPageNum(maxPageNum);
        if (totalRow == 0) {
            response.setTotalPageNum(0);
            response.setPreNum(pageNum);
            response.setNextNum(pageNum);
            response.setBeginNum(0);
            response.setList(Collections.emptyList());
            return response;
        }

        int totalPageNum = totalRow % maxPageNum == 0 ? totalRow / maxPageNum : totalRow / maxPageNum + 1;
        response.setTotalPageNum(totalPageNum);
        response.setPreNum(pageNum > 1 ? pageNum - 1 : pageNum);
        response.setNextNum(pageNum < totalPageNum ? pageNum + 1 : pageNum);
        dto.setBeginNum((pageNum - 1) * maxPageNum);
        response.setBeginNum(dto.getBeginNum());
        response.setList(statisticsMapper.listStatisticsPage(dto));
        return response;
    }

    @Override
    public Statistics getStatisticsById(Integer id) {
        return statisticsMapper.getStatisticsById(id);
    }

    @Override
    public List<ProvinceItemTotalStatisDto> listProvinceItemTotalStatis() {
        return statisticsMapper.listProvinceItemTotalStatis();
    }

    @Override
    public List<AqiDistributeTotalStatisDto> listAqiDistributeTotalStatis() {
        return statisticsMapper.listAqiDistributeTotalStatis();
    }

    @Override
    public List<AqiTrendTotalStatisDto> listAqiTrendTotalStatis() {
        YearMonth now = YearMonth.now();
        List<AqiTrendTotalStatisDto> list = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            AqiTrendTotalStatisDto dto = new AqiTrendTotalStatisDto();
            dto.setMonth(now.minusMonths(i).toString());
            list.add(dto);
        }
        return statisticsMapper.listAqiTrendTotalStatis(list);
    }

    @Override
    public Long getAqiCount() {
        return statisticsMapper.selectCount(null);
    }

    @Override
    public Long getAqiGoodCount() {
        QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("aqi_id", 2);
        return statisticsMapper.selectCount(queryWrapper);
    }

    @Override
    public String getProvinceCoverage() {
        Long useProvince = gridProvinceMapper.selectCount(null);
        double rate = (useProvince / (double) this.provinceNumber) * 100;
        return String.format("%.2f%%", rate);
    }

    @Override
    public String getCityCoverage() {
        Long useCity = gridCityMapper.selectCount(null);
        double rate = (useCity / (double) this.cityNumber) * 100;
        return String.format("%.2f%%", rate);
    }
}
