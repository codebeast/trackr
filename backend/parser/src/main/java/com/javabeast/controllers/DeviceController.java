package com.javabeast.controllers;

import com.javabeast.TrackerMessage;
import com.javabeast.account.Device;
import com.javabeast.account.dto.CreateDevice;
import com.javabeast.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("device")
public class DeviceController {


    private final DeviceService deviceService;

    @Autowired
    public DeviceController(final DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public Device addDevice(@RequestBody final CreateDevice createDevice) {
        return deviceService.saveDevice(createDevice);
    }

    @GetMapping("{imei}")
    public TrackerMessage getLastTrackerMessage(@RequestParam("imei") final String imei) {


            return null;
    }


}
