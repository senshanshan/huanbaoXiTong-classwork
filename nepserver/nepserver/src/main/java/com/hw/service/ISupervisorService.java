package com.hw.service;

import com.hw.po.Supervisor;

public interface ISupervisorService {
    //登录
    Supervisor getSupervisorByIdByPass(Supervisor supervisor);
    //新增注册
    int saveSupervisor(Supervisor supervisor);
    //手机号查询
    Supervisor getSupervisorById(String telId);
}