package com.rjsoft.eurekaclient.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface HelloService {

    @RequestMapping("/sayHello")
    String sayHello(@RequestParam(value = "name") String name);

    @RequestMapping("/sayGoodbye")
    String sayGoodbye(@RequestParam(value = "name") String name);

}
