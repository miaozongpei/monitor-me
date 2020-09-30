package com.m.monitor.me.example.service.impl;

import com.m.monitor.me.example.service.AddrService;
import com.m.monitor.me.example.service.DemoService;
import com.m.monitor.me.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddrServiceImpl implements AddrService {
    @Resource
    private DemoService demoService;
    @Override
    public void findAddr() {
        //demoService.updateUser();
    }
}
