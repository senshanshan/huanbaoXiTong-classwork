package com.hw.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hw.mapper.GridCountyMapper;
import com.hw.po.GridCounty;
import com.hw.service.IGridCountyService;
import org.springframework.stereotype.Service;

@Service
public class GridCountyServiceImpl
        extends ServiceImpl<GridCountyMapper, GridCounty>
        implements IGridCountyService {
}