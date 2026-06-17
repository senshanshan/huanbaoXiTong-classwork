package com.hw.controller;
import com.hw.po.GridTown;
import com.hw.service.IGridTownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/gridTown")
public class GridTownController {

    @Autowired
    private IGridTownService service;

    @PostMapping("/list")
    public List<GridTown> list() {
        return service.list();
    }

    @PostMapping("/save")
    public String save(@RequestBody GridTown obj) {
        service.save(obj);
        return "success";
    }

    @PostMapping("/update")
    public String update(@RequestBody GridTown obj) {
        service.updateById(obj);
        return "success";
    }

    @PostMapping("/del")
    public String del(@RequestBody GridTown obj) {
        service.removeById(obj.getId());
        return "success";
    }
}