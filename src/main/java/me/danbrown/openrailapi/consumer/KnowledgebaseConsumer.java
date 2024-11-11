package me.danbrown.openrailapi.consumer;

import jakarta.jms.MessageConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class KnowledgebaseConsumer extends Consumer {

    public KnowledgebaseConsumer(@Qualifier("knowledgebaseMessageConsumer") MessageConsumer messageConsumer) {
        super(messageConsumer);
    }

    @Override
    void handle(String body) {
        System.out.println(body);
    }
}
