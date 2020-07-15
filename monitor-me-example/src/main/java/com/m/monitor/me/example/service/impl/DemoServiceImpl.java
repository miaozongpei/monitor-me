package com.m.monitor.me.example.service.impl;

import com.m.monitor.me.example.service.DemoService;
import com.m.monitor.me.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private UserService userService;
    @Override
    public void findUser(String userID) {
        userService.find(userID);
        userService.find("Leo");
    }
    public void findUserByName(String name) {
        userService.find(name);
    }
    public void findAddr() {
        try {
            Thread.sleep(5);//find db
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateUser() {
        try {
            Thread.sleep(890);//find db
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
