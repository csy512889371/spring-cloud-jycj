package com.rjsoft.zipkin.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class ZipkinServerApplication {

	public static void main(String[] args) {
		log.info("start execute ZipkinServerApplication....\n");
		System.out.println("start execute ZipkinServerApplication....\n");
		new SpringApplicationBuilder(ZipkinServerApplication.class).bannerMode(Banner.Mode.OFF).run(args);
		System.out.println("end execute ZipkinServerApplication....\n");
		log.info("end execute ZipkinServerApplication....\n");
	}
}
