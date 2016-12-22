package com.javabeast.repo;

import com.javabeast.TrackerMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface TrackerMessageRepo extends MongoRepository<TrackerMessage, String> {

}
