package com.hw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hw.po.Supervisor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SupervisorMapper extends BaseMapper<Supervisor> {
    //公众监督员登录
    @Select("select * from supervisor where tel_id=#{telId} and password=#{password}")
    Supervisor getSupervisorByIdByPass(Supervisor supervisor);
}