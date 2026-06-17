package com.hw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hw.mapper.AqiMapper;
import com.hw.po.Aqi;
import com.hw.service.IAqiService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class AqiServiceImpl extends ServiceImpl<AqiMapper,Aqi> implements IAqiService {

    @Autowired
    private AqiMapper aqiMapper;

    @Override
    public List<Aqi> getAqiByCity(String cityCode) {
        return aqiMapper.selectByCity(cityCode);
    }
}