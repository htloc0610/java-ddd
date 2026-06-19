package com.xxxx.ddd.controller.resource;

import com.xxxx.ddd.application.service.event.impl.EventAppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HiController {
    @Autowired
    private EventAppServiceImpl eventAppServiceImpl;

    @GetMapping("/hi")
    public String hello() {
        return eventAppServiceImpl.sayHi("abc");
    }

    @GetMapping("/hi/v1")
    public String helloV1() {
        return eventAppServiceImpl.sayHi("abc");
    }
}
