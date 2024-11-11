package me.danbrown.railflow.consumer;

import jakarta.jms.MessageConsumer;
import me.danbrown.railflow.handler.TrainStatusHandler;
import me.danbrown.railflow.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import static me.danbrown.railflow.consumer.model.MessageTypes.TRAIN_STATUS;

@Component
public class DarwinConsumer extends Consumer {

    private final TrainStatusHandler trainStatusHandler;

    public DarwinConsumer(@Qualifier("darwinMessageConsumer") MessageConsumer consumer, TrainStatusHandler trainStatusHandler) {
        super(consumer);
        this.trainStatusHandler = trainStatusHandler;
    }

    @Override
    void handle(String body) {
        try {
            Document document = XmlUtils.constructDocument(body);
            trainStatusHandler.handleTrainStatus(document.getElementsByTagName(TRAIN_STATUS.getName()));
        } catch (Exception e) {
            log.error("Error parsing message body", e);
        }
    }
}