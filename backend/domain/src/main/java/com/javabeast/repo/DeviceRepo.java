package com.javabeast.repo;

import com.javabeast.account.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceRepo extends MongoRepository<Device, String> {

    Device findByImei(String imei);

    List<Device> findByAccountId(Object id);

}
