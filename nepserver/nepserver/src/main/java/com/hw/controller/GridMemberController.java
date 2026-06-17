package com.hw.controller;

import com.hw.po.GridMember;
import com.hw.service.IGridMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/gridMember")
public class GridMemberController {
    @Autowired
    private IGridMemberService gridMemberService;

    // 按省份、城市、状态查询列表
    @RequestMapping("/ListGridMemberByProvinceId")
    public List<GridMember> listGridMemberByProvinceId(@RequestBody GridMember gridMember) {
        return gridMemberService.listGridMemberByProIdCityIdState(gridMember);
    }

    // 网格员登录接口
    @RequestMapping("/getGridMemberByCodeByPass")
    public GridMember getGridMemberByCodeByPass(@RequestBody GridMember gridMember) {
        return gridMemberService.getGridMemberByCodeByPass(gridMember);
    }
}