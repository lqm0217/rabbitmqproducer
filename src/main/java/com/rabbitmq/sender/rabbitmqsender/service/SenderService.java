package com.rabbitmq.sender.rabbitmqsender.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.sender.rabbitmqsender.domain.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class SenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendFanout(Project project) {
        sendMessage("amq.fanout", "", project);
    }

    public void sendDirect(Project project) {
        sendMessage("amq.direct", "direct1", project);
    }

    public void sendTopic(Project project) {
        sendMessage("amq.topic", "topics.a", project);
    }

    private void sendMessage(String exchangeName, String routingKey, Project project) {
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
