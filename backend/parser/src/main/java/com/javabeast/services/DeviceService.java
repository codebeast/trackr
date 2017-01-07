package com.javabeast.services;

import com.javabeast.account.Device;
import com.javabeast.account.dto.CreateDevice;
import com.javabeast.repo.AccountRepo;
import com.javabeast.repo.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepo deviceRepo;

    private final AccountRepo accountRepo;

    @Autowired
    public DeviceService(final DeviceRepo deviceRepo, final AccountRepo accountRepo) {
        this.deviceRepo = deviceRepo;
        this.accountRepo = accountRepo;
    }

    public Device saveDevice(final CreateDevice createDevice) {

        final boolean isDeviceValid = checkDataValid(createDevice);
        if (!isDeviceValid) {
            return null;
        }

        return Device.builder().build();
    }

    private boolean checkDataValid(CreateDevice createDevice) {
        return createDevice != null && createDevice.getAccount() != null && createDevice.getDevice() != null;


    }


}
