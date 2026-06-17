package com.hw.controller;
import com.hw.po.GridCounty;
import com.hw.service.IGridCountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/gridCounty")
public class GridCountyController {

    @Autowired
    private IGridCountyService service;

    @PostMapping("/list")
    public List<GridCounty> list() {
        return service.list();
    }

    @PostMapping("/save")
    public String save(@RequestBody GridCounty obj) {
        service.save(obj);
        return "success";
    }

    @PostMapping("/update")
    public String update(@RequestBody GridCounty obj) {
        service.updateById(obj);
        return "success";
    }

    @PostMapping("/del")
    public String del(@RequestBody GridCounty obj) {
        service.removeById(obj.getId());
        return "success";
    }
}