package com.test.makieiev.webshop.util.generation;

import cn.apiclub.captcha.Captcha;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CleanerWorker implements Runnable {

    private static final Logger logger = Logger.getLogger(CleanerWorker.class);

    private final Map<Long, Captcha> captchaValues;
    private final long time;

    public CleanerWorker(Map<Long, Captcha> captchaValues, int minute) {
        this.captchaValues = captchaValues;
        time = TimeUnit.MINUTES.toMillis(minute);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(time);
                removeCaptches();
            }
        } catch (InterruptedException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    private void removeCaptches() {
        List<Long> keys = new ArrayList<>();
        captchaValues.forEach((key, value) -> {
            long timeNow = System.currentTimeMillis();
            long tampTime = value.getTimeStamp().getTime();
            if (timeNow - tampTime > time) {
                keys.add(key);
            }
        });
        keys.forEach(key -> captchaValues.remove(key));
    }

}