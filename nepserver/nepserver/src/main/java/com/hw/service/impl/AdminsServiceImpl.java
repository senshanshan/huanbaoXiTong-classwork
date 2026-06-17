package com.hw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hw.mapper.AdminsMapper;
import com.hw.po.Admins;
import com.hw.service.IAdminsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements IAdminsService {

    @Autowired
    private AdminsMapper adminsMapper;

    @Override
    public Admins getAdminsByCodeByPass(Admins admins) {
        return adminsMapper.getAdminsByCodeByPass(admins);
    }
}