package me.danbrown.railflow.consumer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public abstract class Consumer {

    protected final Logger log;

    private final MessageConsumer messageConsumer;

    public Consumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @PostConstruct
    void init() {
        startConsumer();
    }

    @PreDestroy
    void destroy() throws JMSException {
        messageConsumer.close();
    }

    public void startConsumer() {
        log.info("Starting {}", this.getClass().getSimpleName());
        try {
            messageConsumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            log.error("Exception initializing message listener", e);
        }
    }

    private void processMessage(Message message) {
        try {
            BufferedReader bufferedReader = getBufferedReader(message);
            String body = bufferedReader.lines().collect(Collectors.joining());
            handle(body);
        } catch (JMSException | IOException e) {
            log.error("Failed to read message body {}", message, e);
        }
    }

    private BufferedReader getBufferedReader(Message message) throws JMSException, IOException {
        ByteArrayInputStream byteArrayInputStream;
        if (message instanceof ActiveMQBytesMessage activeMQBytesMessage) {
            byteArrayInputStream = new ByteArrayInputStream(activeMQBytesMessage.getBody(byte[].class));
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
            return new BufferedReader(inputStreamReader);
        } else if (message instanceof ActiveMQTextMessage activeMQBytesMessage) {
            byteArrayInputStream = new ByteArrayInputStream(activeMQBytesMessage.getBody(String.class).getBytes());
            InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8);
            return new BufferedReader(inputStreamReader);
        } else {
            log.error("Unsupported message type {}", message.getClass());
            throw new IllegalArgumentException("Unsupported message type");
        }
    }

    abstract void handle(String body);
}
