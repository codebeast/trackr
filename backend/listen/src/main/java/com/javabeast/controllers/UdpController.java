package com.javabeast.controllers;

import com.javabeast.TrackerPreParsedMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jeffreya on 10/12/2016.
 */
@RestController
public class UdpController {

    @GetMapping
    public String get() {
        return null;
    }

    @PostMapping
    public void post(@RequestBody final TrackerPreParsedMessage trackerPreParsedMessage) {
        System.out.println("UdpController.post");
        System.out.println("trackerPreParsedMessage = [" + trackerPreParsedMessage + "]");
    }

}
