package com.javabeast.repo;

import com.javabeast.account.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccountRepo extends MongoRepository<Account, String> {

}
