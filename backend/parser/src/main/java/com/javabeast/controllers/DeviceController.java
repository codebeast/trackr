package com.javabeast.controllers;

import com.javabeast.account.Device;
import com.javabeast.account.dto.CreateDevice;
import com.javabeast.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("device")
public class DeviceController {


    private final DeviceService deviceService;

    @Autowired
    public DeviceController(final DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PutMapping
    public Device addDevice(final CreateDevice createDevice) {
        return deviceService.saveDevice(createDevice);
    }

}
