package com.hw.service.impl;

import com.hw.mapper.SupervisorMapper;
import com.hw.po.Supervisor;
import com.hw.service.ISupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupervisorServiceImpl implements ISupervisorService {
    @Autowired
    private SupervisorMapper supervisorMapper;

    @Override
    public Supervisor getSupervisorByIdByPass(Supervisor supervisor) {
        return supervisorMapper.getSupervisorByIdByPass(supervisor);
    }

    @Override
    public int saveSupervisor(Supervisor supervisor) {
        return supervisorMapper.insert(supervisor);
    }

    @Override
    public Supervisor getSupervisorById(String telId) {
        return supervisorMapper.selectById(telId);
    }
}