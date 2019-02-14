package com.rjsoft.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


@Service
@RestController
public class HelloServiceImpl implements HelloService {

    @Value("${server.port}")
    String port;

    public String sayHello(String name) {
        return "hello " + name + " ,i am form port:" + port;
    }

    public String sayGoodbye(String name) {
        return "hello " + name + " ,i am form port:" + port;
    }
}
