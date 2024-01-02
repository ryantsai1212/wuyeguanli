package com.example.backend.controller;

import com.example.backend.pojo.Admin;
import com.example.backend.common.Result;
import com.example.backend.service.AdminService;
import com.example.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;


@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public Result selectAdminByUsername(String username, String password){
        Admin admin = adminService.AdminLogin(username, password);
        System.out.println("账号："+username+", 密码："+password);
        if(admin == null) return Result.error("账号或密码错误！");
        else{
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", admin.getId());
            claims.put("username", admin.getUsername());

            char type = admin.getType();
            System.out.println(type);
            if(Objects.equals(type, '0')) claims.put("type","管理员");
            else if(Objects.equals(type, '1')) claims.put("type","住户");
            else if(Objects.equals(type, '2')) claims.put("type","物业人员");

            String jwt = JwtUtils.generateJwt(claims);
            //下发jwt令牌
            return Result.success(jwt);
        }
    }
}