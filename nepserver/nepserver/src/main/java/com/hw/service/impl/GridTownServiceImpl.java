package com.hw.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hw.mapper.GridTownMapper;
import com.hw.po.GridTown;
import com.hw.service.IGridTownService;
import org.springframework.stereotype.Service;

@Service
public class GridTownServiceImpl
        extends ServiceImpl<GridTownMapper, GridTown>
        implements IGridTownService {
}