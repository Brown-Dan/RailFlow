package me.danbrown.openrailapi.consumer;

import jakarta.annotation.PostConstruct;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
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

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    private final MessageConsumer messageConsumer;

    public Consumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @PostConstruct
    void init() {
        startConsumer();
    }

    public void startConsumer() {
        LOG.info("Starting Consumer");
        try {
            messageConsumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            LOG.error("Exception initializing message listener", e);
        }
    }

    private void processMessage(Message message) {
        try {
            BufferedReader bufferedReader = getBufferedReader(message);
            String xmlContent = bufferedReader.lines().collect(Collectors.joining());
            handle(xmlContent);
        } catch (JMSException | IOException e) {
            LOG.error("Failed to read message body {}", message, e);
        }
    }

    private BufferedReader getBufferedReader(Message message) throws JMSException, IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBody(byte[].class));
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);

        return new BufferedReader(inputStreamReader);
    }

    abstract void handle(String body);
}
