package com.javabeast.repo;

import com.javabeast.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface PersonRepo extends MongoRepository<Person, String> {

}
