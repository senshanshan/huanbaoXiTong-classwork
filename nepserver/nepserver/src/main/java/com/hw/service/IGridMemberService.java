package com.hw.service;

import com.hw.po.GridMember;
import java.util.List;

public interface IGridMemberService {
    // 根据省份id、城市id、状态查询网格员列表
    List<GridMember> listGridMemberByProIdCityIdState(GridMember gridMember);
    // 账号密码登录
    GridMember getGridMemberByCodeByPass(GridMember gridMember);
}