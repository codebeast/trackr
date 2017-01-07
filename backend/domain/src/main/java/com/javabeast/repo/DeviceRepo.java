package com.javabeast.repo;

import com.javabeast.account.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface DeviceRepo extends MongoRepository<Device, String> {

}
