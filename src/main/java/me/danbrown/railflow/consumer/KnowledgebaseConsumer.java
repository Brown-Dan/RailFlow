package me.danbrown.railflow.consumer;

import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import me.danbrown.railflow.service.SmsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class KnowledgebaseConsumer extends Consumer {

    public KnowledgebaseConsumer(@Qualifier("knowledgebaseMessageConsumer") MessageConsumer messageConsumer) {
        super(messageConsumer);
    }

    @Override
    void handle(String body) {
        new SmsService().sendSms("Received knowledgebase message: %s".formatted(body));
    }

    @Override
    boolean filter(Message message) {
        return true;
    }
}
