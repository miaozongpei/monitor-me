package com.m.monitor.me.example.service.impl;

import com.m.monitor.me.example.service.AddrService;
import com.m.monitor.me.example.service.DemoService;
import com.m.monitor.me.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private AddrService addrService;
    @Resource
    private UserService userService;
    @Override
    public String find(String userId) {
        addrService.findAddr();
        userService.findName(userId);
        return "Leo";
    }

    @Override
    public String findName(String userId) {
        return null;
    }
}
