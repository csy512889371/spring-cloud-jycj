package com.rjsoft.servicezuul.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoHystrixController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/consumer")
    public String dc() {
        return consumerService.consumer();
    }

    @Service
    class ConsumerService {
        @HystrixCommand(fallbackMethod = "fallback")
        public String consumer() {
            return "test";
        }

        public String fallback() {
            return "fallback";
        }

    }

}