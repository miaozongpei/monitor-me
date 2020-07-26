package com.m.monitor.me.example;

import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.example.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorMeExampleApplication.class)
@Lazy
public class DemoServiceTests {
	@Resource
	private DemoService demoService;
	@Test
	public void findUserTest() {

		demoService.findUser("123456");
		//demoService.findUserByName("miao");
		//demoService.updateUser();
		//MonitorPointCollector.printPointIntegrator();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findUserThreadTest() throws ExecutionException, InterruptedException {
	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 220; i++) {
            final int ii = i;
			Future future= fixedThreadPool.submit(() -> {
				//demoService.findUser("123456");
				//demoService.findUserByName("miao");
				demoService.updateUser();
            });
			future.get();
        }

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 100; i++) {
			final int ii = i;
			Future future= fixedThreadPool.submit(() -> {
				//demoService.findUser("123456");
				//demoService.findUserByName("miao");
				demoService.updateUser();
			});
			future.get();
		}
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
