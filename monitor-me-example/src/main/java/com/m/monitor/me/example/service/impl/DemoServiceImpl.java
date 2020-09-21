package com.m.monitor.me.example.service.impl;

import com.m.monitor.me.example.service.AddrService;
import com.m.monitor.me.example.service.DemoService;
import com.m.monitor.me.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private UserService userService;

    @Resource
    private AddrService addrService;

    @Override
    public void findUser(String userID) {
        userService.find(userID);
        userService.find("xx");

    }
    public void findUserByName(String name) {
        userService.find(name);
    }
    public void findAddr1() {
        addrService.findAddr();
    }
    public void updateUser() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
