package com.hw.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hw.mapper.GridCityMapper;
import com.hw.po.GridCity;
import com.hw.service.IGridCityService;
import org.springframework.stereotype.Service;

@Service
public class GridCityServiceImpl
        extends ServiceImpl<GridCityMapper, GridCity>
        implements IGridCityService {
}