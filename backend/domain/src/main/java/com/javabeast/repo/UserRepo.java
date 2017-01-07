package com.javabeast.repo;

import com.javabeast.account.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepo extends MongoRepository<User, String> {

    User findByEmail(String email);

}
