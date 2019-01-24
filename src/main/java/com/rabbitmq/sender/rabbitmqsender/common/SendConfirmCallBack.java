package com.rabbitmq.sender.rabbitmqsender.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("CorrelationData:{}", correlationData);
        if (ack) {
            log.info("Send message success.");
        } else {
            log.info("Send message fail!");
            log.error("Case:{}", cause);
        }
    }
}
