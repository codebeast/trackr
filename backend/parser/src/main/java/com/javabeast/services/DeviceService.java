package com.javabeast.services;

import com.javabeast.TrackerMessage;
import com.javabeast.account.Account;
import com.javabeast.account.Device;
import com.javabeast.account.dto.CreateDevice;
import com.javabeast.domain.gecode.DeviceState;
import com.javabeast.repo.AccountRepo;
import com.javabeast.repo.DeviceRepo;
import com.javabeast.repo.TrackerMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepo deviceRepo;

    private final AccountRepo accountRepo;

    private final TrackerMessageRepo trackerMessageRepo;

    @Autowired
    public DeviceService(final DeviceRepo deviceRepo, final AccountRepo accountRepo, final TrackerMessageRepo trackerMessageRepo) {
        this.deviceRepo = deviceRepo;
        this.accountRepo = accountRepo;
        this.trackerMessageRepo = trackerMessageRepo;
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
        final Account loadedAccount = accountRepo.findByName(account.getName());
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


    public List<JourneyDTO> getDeviceJourneyDTOStartEndOnly(final String imei) {
        final List<Journey> deviceJourneyStartEndOnly = getDeviceJourneys(imei);
        final List<JourneyDTO> journeyDTOList = new ArrayList<>();
        for (final Journey journey : deviceJourneyStartEndOnly) {
            final List<TrackerMessage> trackerMessageList = journey.getTrackerMessageList();
            final TrackerMessage start = trackerMessageList.get(0);
            final TrackerMessage end = trackerMessageList.get(trackerMessageList.size() - 1);

            final JourneyDTO journeyDTO = JourneyDTO.builder().startMessage(start).endMessage(end).build();
            journeyDTOList.add(journeyDTO);

        }
        return journeyDTOList;
    }

    private List<Journey> getDeviceJourneys(final String imei) {

        final List<TrackerMessage> byImeiOrderByTimestampAsc = trackerMessageRepo.findByImeiOrderByTimestampAsc(imei);
        String lastGpsPosition = byImeiOrderByTimestampAsc.get(0).getGpsElement().getLatLngString();
        final List<Journey> journeys = new ArrayList<>();
        DeviceState lastState = DeviceState.STOPPED;
        List<TrackerMessage> journey = null;
        TrackerMessage lastMovingMessage = null;

        for (final TrackerMessage trackerMessage : byImeiOrderByTimestampAsc) {
            final DeviceState previousState = lastState;
            final String currentGpsPosition = trackerMessage.getGpsElement().getLatLngString();
            if (!currentGpsPosition.equals(lastGpsPosition)) {
                if (lastMovingMessage != null) {
                    final long minutes = getTimeDifferenceInMinutes(lastMovingMessage, trackerMessage);
                    if (minutes > 10 && journey != null) {
                        journeys.add(Journey.builder().trackerMessageList(journey).build());
                        journey = new ArrayList<>();
                    }
                }
                if (lastState == DeviceState.STOPPED) {
                    if (journey != null) {
                        journeys.add(Journey.builder().trackerMessageList(journey).build());
                    }
                    journey = new ArrayList<>();
                }
                lastState = DeviceState.MOVING;
                lastGpsPosition = currentGpsPosition;
                lastMovingMessage = trackerMessage;
            } else {
                if (lastMovingMessage != null) {
                    final long minutes = getTimeDifferenceInMinutes(lastMovingMessage, trackerMessage);
                    if (minutes < 7) {
                        lastState = DeviceState.IDLE;
                    } else {
                        lastState = DeviceState.STOPPED;
                    }
                } else {
                    lastState = DeviceState.STOPPED;
                }
            }
            if (journey != null && previousState != DeviceState.STOPPED) {
                journey.add(trackerMessage);
            }
        }
        return strip0Journeys(journeys);
    }

    private List<Journey> strip0Journeys(List<Journey> journeys) {
        final Iterator<Journey> iterator = journeys.iterator();
        while (iterator.hasNext()) {
            final Journey deviceJourney = iterator.next();
            final List<TrackerMessage> trackerMessageList = deviceJourney.getTrackerMessageList();
            if (trackerMessageList.isEmpty()) {
                iterator.remove();
            }
        }
        return journeys;
    }

    private long getTimeDifferenceInMinutes(final TrackerMessage lastMovingMessage, final TrackerMessage trackerMessage) {
        final long time = lastMovingMessage.getTimestamp().getTime();
        final long currentTime = trackerMessage.getTimestamp().getTime();
        final long diff = currentTime - time;
        final long minute = 60000;
        return diff / minute;
    }
}
