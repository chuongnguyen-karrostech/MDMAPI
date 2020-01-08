package com.mdmdashboard;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.mdmdashboard" })
public class MdmdashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdmdashboardApplication.class, args);
	}
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	}
}
