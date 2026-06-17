package com.hw.controller;

import com.hw.po.Admins;
import com.hw.service.IAdminsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admins")
public class AdminsController {

    @Autowired
    private IAdminsService adminsService;

    /**
     * 管理员登录接口
     * 地址：http://localhost:8080/nepm/admins/getAdminsByCodeByPass
     * post请求，body：{"adminCode":"admin","password":"123"}
     */
    @PostMapping("/getAdminsByCodeByPass")
    public Admins getAdminsByCodeByPass(@RequestBody Admins admins){
        log.info("管理员登录请求，adminCode: {}", admins.getAdminCode());
        Admins result = adminsService.getAdminsByCodeByPass(admins);
        if (result != null) {
            log.info("管理员登录成功，adminCode: {}", admins.getAdminCode());
        } else {
            log.warn("管理员登录失败，adminCode: {}，账号或密码错误", admins.getAdminCode());
        }
        return result;
    }
}
