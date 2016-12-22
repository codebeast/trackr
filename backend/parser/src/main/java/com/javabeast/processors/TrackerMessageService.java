
package com.javabeast.processors;

import com.javabeast.TrackerMessage;
import com.javabeast.repo.TrackerMessageRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Created by jeffreya on 06/11/2016.
 */

@Service
public class TrackerMessageService {

    private final TrackerMessageRepo trackerMessageRepo;

    @Autowired
    public TrackerMessageService(final TrackerMessageRepo trackerMessageRepo) {
        this.trackerMessageRepo = trackerMessageRepo;
    }

    public void save(final TrackerMessage trackerMessage) {
        trackerMessageRepo.save(trackerMessage);
    }

}