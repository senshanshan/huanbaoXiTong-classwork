package com.hw.controller;

import com.hw.po.GridProvince;
import com.hw.service.IGridProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/gridProvince")
public class GridProvinceController {

    @Autowired
    private IGridProvinceService gridProvinceService;

    // POST 接口：获取所有省份列表
    @PostMapping("/list")
    public List<GridProvince> list() {
        return gridProvinceService.list();
    }
}