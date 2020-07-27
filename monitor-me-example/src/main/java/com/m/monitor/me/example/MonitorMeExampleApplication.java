package com.m.monitor.me.example;

import com.m.monitor.me.client.enable.EnableMonitorMe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMonitorMe
public class MonitorMeExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonitorMeExampleApplication.class, args);
	}
}
