package com.javabeast.controllers;

import com.javabeast.TrackerMessage;
import com.javabeast.account.Device;
import com.javabeast.account.dto.CreateDevice;
import com.javabeast.services.DeviceService;
import com.javabeast.services.Journey;
import com.javabeast.services.JourneyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("device")
public class DeviceController {


    private final DeviceService deviceService;

    @Autowired
    public DeviceController(final DeviceService deviceService) {
        this.deviceService = deviceService;
    }

//    @PostMapping
//    public Device addDevice(@RequestBody final CreateDevice createDevice) {
//        return deviceService.saveDevice(createDevice);
//    }
//
//    @GetMapping("{imei}")
//    public List<JourneyDTO> getDeviceJourneys(@PathVariable("imei") final String imei) {
//        return getDeviceJourneyDTOStartEndOnly(imei);
//    }
//
//    private List<JourneyDTO> getDeviceJourneyDTOStartEndOnly(final String imei) {
//        final List<Journey> deviceJourneyStartEndOnly = deviceService.getDeviceJourneys(imei);
//        final List<JourneyDTO> journeyDTOList = new ArrayList<>();
//        for (final Journey journey : deviceJourneyStartEndOnly) {
//            final List<TrackerMessage> trackerMessageList = journey.getTrackerMessageList();
//            final TrackerMessage start = trackerMessageList.get(0);
//            final TrackerMessage end = trackerMessageList.get(trackerMessageList.size() - 1);
//
//            final JourneyDTO journeyDTO = JourneyDTO.builder().startMessage(start).endMessage(end).build();
//            journeyDTOList.add(journeyDTO);
//
//        }
//        return journeyDTOList;
//    }


}
