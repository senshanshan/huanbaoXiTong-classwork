package com.hw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hw.po.GridMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GridMemberMapper extends BaseMapper<GridMember> {
    // 网格员账号密码登录：gm_code + password
    @Select("select * from grid_member where gm_code=#{gmCode} and password=#{password}")
    GridMember getGridMemberByCodeByPass(GridMember gridMember);
}