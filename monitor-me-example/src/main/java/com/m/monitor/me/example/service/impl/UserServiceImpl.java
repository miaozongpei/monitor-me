package com.m.monitor.me.example.service.impl;

import com.m.monitor.me.example.service.DemoService;
import com.m.monitor.me.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private DemoService demoService;
    @Override
    public String find(String userId) {
        demoService.findAddr();
        try {
            Thread.sleep(100);//find db
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Leo";
    }
}
