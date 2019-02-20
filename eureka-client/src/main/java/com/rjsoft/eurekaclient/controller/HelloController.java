package com.rjsoft.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Service
@RestController()
@RequestMapping("/hello")
public class HelloController implements HelloService {

    @Value("${server.port}")
    String port;

    public String sayHello(String name) {
        System.out.println("request is coming...");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            System.out.println("线程被打断... " + e.getMessage());
        }
        return "hello " + name + " ,i am form port:" + port;
    }

    public String sayGoodbye(String name) {
        return "hello " + name + " ,i am form port:" + port;
    }
}
