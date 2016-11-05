package com.javabeast.ampq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jeffreya on 05/11/2016.
 *
 */

public class Receiver {
    private Log log = LogFactory.getLog(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
