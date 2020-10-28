package com.m.monitor.me.example;

import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.example.service.DemoService;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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

		//demoService.findUser("123456");
		demoService.findUserByName("miao");
		demoService.findUserByName("miao");

		//demoService.updateUser();
		//demoService.findUserByName("miao");
		//demoService.updateUser();
		//MonitorPointCollector.printPointIntegrator();
		/*try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();


	//@PerfTest(threads = 1000, duration = 30000)
	@PerfTest(threads = 1000, invocations = 1)

	@Test
	public void findUserThreadTest() throws ExecutionException, InterruptedException {
		demoService.findUserByName("miao");


	}


}
