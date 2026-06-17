package com.hw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hw.po.Admins;

public interface IAdminsService extends IService<Admins> {
    // 登录方法
    Admins getAdminsByCodeByPass(Admins admins);
}