package me.danbrown.railflow.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

    public void sendSms(String content) {
        LOG.info("Attempting to send SMS");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(PHONE_TO, PHONE_FROM, content).create();
    }
}