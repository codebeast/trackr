package com.javabeast.repo;

import com.javabeast.TrackerMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface TrackerMessageRepo extends MongoRepository<TrackerMessage, String> {

    //db.trackerMessage.find({"imei" : "868590027340513"}).sort({"timestamp":-1}).limit(1)

    TrackerMessage findTop1ByImeiOrderByTimestamp(String imei);

}
