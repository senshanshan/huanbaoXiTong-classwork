package com.hw.controller;

import com.hw.po.Aqi;
import com.hw.service.IAqiService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/aqi")
public class AqiController {

    @Autowired
    private IAqiService aqiService;

    // 查询全部
    @GetMapping("/list")
    public List<Aqi> listAll(){
        return aqiService.list();
    }

    @RequestMapping("/listAqiAll")
    public List<Aqi> listAqiAll(){
        return aqiService.list();
    }

    // 根据城市编码查询
    @GetMapping("/city/{code}")
    public List<Aqi> getByCity(@PathVariable String code){
        return aqiService.getAqiByCity(code);
    }

    // 新增
    @PostMapping("/add")
    public boolean add(@RequestBody Aqi aqi){
        return aqiService.save(aqi);
    }

    // 修改
    @PutMapping("/update")
    public boolean update(@RequestBody Aqi aqi){
        return aqiService.updateById(aqi);
    }

    // 删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return aqiService.removeById(id);
    }
}
