package me.danbrown.railflow.consumer.exception;

import jakarta.jms.ExceptionListener;
import jakarta.jms.JMSException;
import me.danbrown.railflow.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler implements ExceptionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public void onException(JMSException e) {
        new SmsService().sendSms("JMS Exception Occurred - Darwin Consumer is shutting down");
        LOG.error("JMS exception occurred.  Shutting down client.", e);
    }
}
