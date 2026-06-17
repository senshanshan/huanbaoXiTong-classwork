package com.hw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hw.po.Admins;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminsMapper extends BaseMapper<Admins> {
    // 自定义登录SQL：根据账号密码查询管理员
    @Select("select * from admins where admin_code=#{adminCode} and password=#{password}")
    Admins getAdminsByCodeByPass(Admins admins);
}