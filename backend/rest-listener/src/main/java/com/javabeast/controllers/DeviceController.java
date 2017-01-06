package com.javabeast.controllers;

import com.javabeast.devices.PhoneMessage;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("devicemessage")
public class DeviceController {


    @GetMapping
    public String get() {
        System.out.println("DeviceController.get");
        return "get";
    }

    @PostMapping
    public Boolean post(@RequestBody PhoneMessage phoneMessage) {
        System.out.println("DeviceController.post");
        System.out.println(phoneMessage);
        return true;
    }
}
