package com.javabeast.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("devicemessage")
public class GreetingController {


    @GetMapping
    public String get() {
        System.out.println("GreetingController.get");
        return "get";
    }

    @PostMapping
    public void post() {
        System.out.println("GreetingController.post");
    }
}
