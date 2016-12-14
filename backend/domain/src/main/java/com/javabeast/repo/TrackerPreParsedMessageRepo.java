package com.javabeast.repo;

import com.javabeast.TrackerPreParsedMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface TrackerPreParsedMessageRepo extends MongoRepository<TrackerPreParsedMessage, String> {

}
