package com.rabbitmq.producer.rabbitmqproducer.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.rabbitmqproducer.domain.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.UUID;

@Slf4j
@Service
public class ProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendFanout(Project project) throws Exception {
        sendMessage("amq.fanout", "", project);
    }

    public void sendDirect(Project project) throws Exception {
        sendMessage("amq.direct", "direct1", project);
    }

    public void sendTopic(Project project) throws Exception {
        sendMessage("amq.topic", "topics.a", project);
    }

    @Retryable(value = {ConnectException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 1))
    private void sendMessage(String exchangeName, String routingKey, Project project) throws Exception {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        String messageBoby = "";
        try {
            messageBoby = objectMapper.writeValueAsString(project);
            log.info("Message Body: {}", messageBoby);
        } catch (JsonProcessingException e) {
            log.error("Json conver error: {}", e.getMessage());
            return;
        }

        Message message = MessageBuilder.withBody(messageBoby.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .build();
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, correlationId);
    }

}
