package com.javabeast.controllers;

import com.javabeast.TrackerPreParsedMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController(value = "upd")
public class UdpController {

    @GetMapping
    public String get() {
        return "This is the udp controller";
    }

    @PostMapping
    public void post(@RequestBody final TrackerPreParsedMessage trackerPreParsedMessage) {
        System.out.println("UdpController.post");
        System.out.println("trackerPreParsedMessage = [" + trackerPreParsedMessage + "]");
    }

}
