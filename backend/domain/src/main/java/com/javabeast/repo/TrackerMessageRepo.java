package com.javabeast.repo;

import com.javabeast.TrackerMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrackerMessageRepo extends MongoRepository<TrackerMessage, String> {

    TrackerMessage findTop1ByImeiOrderByTimestampDesc(String imei);

    List<TrackerMessage> findByImeiOrderByTimestampAsc(String imei);
}
