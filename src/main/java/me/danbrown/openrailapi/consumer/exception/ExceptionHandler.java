package me.danbrown.openrailapi.consumer.exception;

import jakarta.jms.ExceptionListener;
import jakarta.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler implements ExceptionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public void onException(JMSException e) {
        LOG.error("JMS exception occurred.  Shutting down client.", e);
    }
}
