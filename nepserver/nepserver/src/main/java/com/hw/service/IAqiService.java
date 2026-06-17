package com.hw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hw.po.Aqi;
import java.util.List;

public interface IAqiService extends IService<Aqi> {
    List<Aqi> getAqiByCity(String cityCode);
}