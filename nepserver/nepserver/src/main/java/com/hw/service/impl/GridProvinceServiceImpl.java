package com.hw.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hw.mapper.GridProvinceMapper;
import com.hw.po.GridProvince;
import com.hw.service.IGridProvinceService;
import org.springframework.stereotype.Service;

@Service
public class GridProvinceServiceImpl
        extends ServiceImpl<GridProvinceMapper, GridProvince>
        implements IGridProvinceService {
}