package com.javabeast.controllers;

import com.javabeast.devices.PhoneMessage;
import com.javabeast.service.PhoneMessageService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("devicemessage")
public class DeviceController {


    private final PhoneMessageService phoneMessageService;

    public DeviceController(final PhoneMessageService phoneMessageService) {
        this.phoneMessageService = phoneMessageService;
    }

    @GetMapping
    public String get() {
        System.out.println("DeviceController.get");
        return "get";
    }

    @PostMapping
    public Boolean post(@RequestBody PhoneMessage phoneMessage) {
        phoneMessageService.convertAndPush(phoneMessage);
        return true;
    }

    @PostMapping("multiple")
    public Boolean post(@RequestBody PhoneMessage[] phoneMessages) {
        for (final PhoneMessage phoneMessage : phoneMessages) {
            post(phoneMessage);
        }
        return true;
    }
}
