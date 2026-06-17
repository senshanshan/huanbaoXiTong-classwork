package com.hw.controller;
import com.hw.po.GridCity;
import com.hw.service.IGridCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/gridCity")
public class GridCityController {

    @Autowired
    private IGridCityService service;

    @PostMapping("/list")
    public List<GridCity> list() {
        return service.list();
    }

    @PostMapping("/save")
    public String save(@RequestBody GridCity obj) {
        service.save(obj);
        return "success";
    }

    @PostMapping("/update")
    public String update(@RequestBody GridCity obj) {
        service.updateById(obj);
        return "success";
    }

    @PostMapping("/del")
    public String del(@RequestBody GridCity obj) {
        service.removeById(obj.getCityId());
        return "success";
    }
}