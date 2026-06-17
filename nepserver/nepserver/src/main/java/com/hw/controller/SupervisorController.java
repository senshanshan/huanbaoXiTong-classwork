package com.hw.controller;

import com.hw.po.Supervisor;
import com.hw.service.ISupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {
    @Autowired
    private ISupervisorService supervisorService;

    //公众监督员登录接口
    @RequestMapping("/getSupervisorByIdByPass")
    public Supervisor getSupervisorByIdByPass(@RequestBody Supervisor supervisor) {
        return supervisorService.getSupervisorByIdByPass(supervisor);
    }

    //公众监督员注册接口
    @RequestMapping("/saveSupervisor")
    public int saveSupervisor(@RequestBody Supervisor supervisor) {
        return supervisorService.saveSupervisor(supervisor);
    }

    //手机号验证是否注册
    @RequestMapping("/getsupervisorById")
    public Supervisor getsupervisorById(@RequestBody Supervisor supervisor) {
        return supervisorService.getSupervisorById(supervisor.getTelId());
    }

    @RequestMapping("/getSupervisorById")
    public Supervisor getSupervisorById(@RequestBody Supervisor supervisor) {
        return supervisorService.getSupervisorById(supervisor.getTelId());
    }
}
