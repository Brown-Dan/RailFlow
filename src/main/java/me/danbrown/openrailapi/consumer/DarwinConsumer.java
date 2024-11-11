package me.danbrown.openrailapi.consumer;

import jakarta.annotation.PostConstruct;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import me.danbrown.openrailapi.mapper.TrainStatusHandler;
import me.danbrown.openrailapi.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import static me.danbrown.openrailapi.consumer.model.MessageTypes.TRAIN_STATUS;

@Component
public class DarwinConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(DarwinConsumer.class);

    private final MessageConsumer messageConsumer;
    private final TrainStatusHandler trainStatusHandler;

    public DarwinConsumer(@Qualifier("darwinMessageConsumer") MessageConsumer messageConsumer, TrainStatusHandler trainStatusHandler) {
        this.messageConsumer = messageConsumer;
        this.trainStatusHandler = trainStatusHandler;
    }

    @PostConstruct
    void init() {
        startConsumer();
    }

    public void startConsumer() {
        LOG.info("Starting Darwin Consumer");
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
            map(xmlContent);
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

    void map(String xml) {
        try {
            Document document = XmlUtils.constructDocument(xml);

            trainStatusHandler.handleTrainStatus(document.getElementsByTagName(TRAIN_STATUS.getName()));
        } catch (Exception e) {
            LOG.error("Error parsing XML", e);
        }
    }
}