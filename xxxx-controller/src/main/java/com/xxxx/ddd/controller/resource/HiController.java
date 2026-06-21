package com.xxxx.ddd.controller.resource;

import com.xxxx.ddd.application.service.event.impl.EventAppServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("hello")
public class HiController {
    @Autowired
    private EventAppServiceImpl eventAppServiceImpl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hi")
    @RateLimiter(name = "backendA", fallbackMethod = "fallbackHello")
    public String hello() {
        return eventAppServiceImpl.sayHi("abc");
    }

    public String fallbackHello(Throwable throwable) {
        return "Fallback: Too many requests: " + throwable.getMessage();
    }

    @GetMapping("/hi/v1")
    @RateLimiter(name = "backendB", fallbackMethod = "fallbackHelloV1")
    public String helloV1() {
        return eventAppServiceImpl.sayHi("abc");
    }

    public String fallbackHelloV1(Throwable throwable) {
        return "Fallback: Too many requests: " + throwable.getMessage();
    }

    @GetMapping("/circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackHelloV2")
    public String circuitBreaker() {
        int randomId = ThreadLocalRandom.current().nextInt(1, 21);
        String url = "https://fakestoreapi.com/products/" + randomId;
        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackHelloV2(Throwable throwable) {
        return "Fallback: Too many requests: " + throwable.getMessage();
    }
}
