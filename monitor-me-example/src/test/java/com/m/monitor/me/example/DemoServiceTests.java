package com.m.monitor.me.example;

import com.m.monitor.me.client.handler.point.MonitorPointContext;
import com.m.monitor.me.example.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorMeExampleApplication.class)
@Lazy
public class DemoServiceTests {
	@Resource
	private DemoService demoService;
	@Test
	public void findUserTest() {
		demoService.findUser("123456");
		demoService.findUserByName("miao");
		demoService.updateUser();
		MonitorPointContext.printAll();
	}

}
