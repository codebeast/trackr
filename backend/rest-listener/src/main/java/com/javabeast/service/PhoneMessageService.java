package com.javabeast.service;

import com.javabeast.TrackerMessage;
import com.javabeast.devices.PhoneMessage;
import com.javabeast.teltonikia.GpsElement;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;



@Service
public class PhoneMessageService {


    @Value("${trackr.trackermessage.queue:trackermessage}")
    private String trackermessageQueue;

    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public PhoneMessageService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndPush(final PhoneMessage phoneMessage) {
        final TrackerMessage trackerMessage = convertToTrackerMessage(phoneMessage);
        pushMessage(trackerMessage);
    }

    private void pushMessage(final TrackerMessage trackerMessage) {
        rabbitTemplate.convertAndSend(trackermessageQueue, trackerMessage);
    }

    private TrackerMessage convertToTrackerMessage(final PhoneMessage phoneMessage) {
        return TrackerMessage.builder()
                .imei(phoneMessage.getImei())
                .timestamp(new Date(phoneMessage.getSystemTimestamp()))
                .gpsElement(GpsElement.builder()
                        .latitude(phoneMessage.getLat())
                        .longitude(phoneMessage.getLng())
                        .satellites((int) Math.round(phoneMessage.getAccuracy()))
                        .speed((long) phoneMessage.getSpeed())
                        .build())
                .build();
    }

}
