package com.m.monitor.me.example;

import com.m.monitor.me.client.enable.EnableMonitorMeConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMonitorMeConfiguration
public class MonitorMeExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonitorMeExampleApplication.class, args);
	}
}
