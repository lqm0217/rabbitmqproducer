package com.rabbitmq.producer.rabbitmqproducer.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

@Slf4j
@Component
public class ProducerConfirmCallBack implements RabbitTemplate.ConfirmCallback {
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
