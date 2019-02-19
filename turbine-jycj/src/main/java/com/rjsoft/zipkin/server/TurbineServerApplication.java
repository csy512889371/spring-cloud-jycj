package com.rjsoft.zipkin.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class TurbineServerApplication {

	public static void main(String[] args) {
		log.info("start execute TurbineServerApplication....\n");
		System.out.println("start execute TurbineServerApplication....\n");
		new SpringApplicationBuilder(TurbineServerApplication.class).bannerMode(Banner.Mode.OFF).run(args);
		System.out.println("end execute TurbineServerApplication....\n");
		log.info("end execute TurbineServerApplication....\n");
	}
}
