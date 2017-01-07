package com.javabeast.services;

import com.javabeast.account.Account;
import com.javabeast.account.Device;
import com.javabeast.account.dto.CreateDevice;
import com.javabeast.repo.AccountRepo;
import com.javabeast.repo.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

        final Account account = createDevice.getAccount();
        final Account loadedAccount = checkAccountExists(account);
        if (loadedAccount == null) {
            return null;
        }

        final Device device = checkDeviceUnique(createDevice);
        if (device == null) {
            return null;
        }

        device.setAccount(loadedAccount);
        return deviceRepo.save(device);
    }

    private Account checkAccountExists(Account account) {
        final Account loadedAccount = accountRepo.findOne(account.getId().toString());
        if (loadedAccount == null) {
            System.out.println("Account not found");
            return null;
        }
        return loadedAccount;
    }

    private Device checkDeviceUnique(CreateDevice createDevice) {
        final Device device = createDevice.getDevice();
        final Device byImei = deviceRepo.findByImei(device.getImei());
        if (byImei != null) {
            System.out.println("Device already exists");
            return null;
        }
        return device;
    }

    private boolean checkDataValid(CreateDevice createDevice) {
        return createDevice != null && createDevice.getAccount() != null && createDevice.getDevice() != null;


    }


}
