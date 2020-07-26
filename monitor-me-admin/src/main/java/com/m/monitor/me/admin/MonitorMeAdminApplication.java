package com.m.monitor.me.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.m.monitor.me"})

public class MonitorMeAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonitorMeAdminApplication.class, args);
	}
}
