package me.danbrown.openrailapi.config;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import me.danbrown.openrailapi.consumer.exception.ExceptionHandler;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConsumerConfig {

    @Bean
    public MessageConsumer knowledgebaseMessageConsumer(ExceptionHandler exceptionHandler,
                                                        @Value("${knowledgebase.broker.url}") String brokerUrl,
                                                        @Value("${knowledgebase.topic}") String topic,
                                                        @Value("${knowledgebase.username}") String username,
                                                        @Value("${knowledgebase.password}") String password) throws JMSException {
        return createConsumer(brokerUrl, topic, username, password, exceptionHandler);
    }

    @Bean
    public MessageConsumer darwinMessageConsumer(ExceptionHandler exceptionHandler,
                                                 @Value("${darwin.broker.url}") String brokerUrl,
                                                 @Value("${darwin.topic}") String topic,
                                                 @Value("${darwin.username}") String username,
                                                 @Value("${darwin.password}") String password) throws JMSException {
        return createConsumer(brokerUrl, topic, username, password, exceptionHandler);
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }

    private MessageConsumer createConsumer(String brokerUrl, String topic, String username, String password, ExceptionHandler exceptionHandler) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connectionFactory.setWatchTopicAdvisories(false);

        Connection connection = connectionFactory.createConnection(username, password);
        connection.setExceptionListener(exceptionHandler);
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        return session.createConsumer(session.createTopic(topic));
    }
}
