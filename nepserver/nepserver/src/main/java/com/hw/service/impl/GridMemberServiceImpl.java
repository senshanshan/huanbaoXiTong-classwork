package com.hw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hw.mapper.GridMemberMapper;
import com.hw.po.GridMember;
import com.hw.service.IGridMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GridMemberServiceImpl implements IGridMemberService {
    @Autowired
    private GridMemberMapper gridMemberMapper;

    // 多条件：省份、城市、启用状态查询
    @Override
    public List<GridMember> listGridMemberByProIdCityIdState(GridMember gridMember) {
        QueryWrapper<GridMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", gridMember.getProvinceId());
        queryWrapper.eq("city_id", gridMember.getCityId());
        queryWrapper.eq("state", gridMember.getState());
        return gridMemberMapper.selectList(queryWrapper);
    }

    // 登录查询
    @Override
    public GridMember getGridMemberByCodeByPass(GridMember gridMember) {
        return gridMemberMapper.getGridMemberByCodeByPass(gridMember);
    }
}