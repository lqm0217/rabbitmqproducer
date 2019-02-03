package com.rabbitmq.producer.rabbitmqproducer.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducerReturnCallBack implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("Message:{}", message.toString());
        log.info("ReplyCode:{}" + replyCode);
        log.info("ReplyText:{}" + replyText);
        log.info("Exchange:{}" + exchange);
        log.info("RoutingKey:{}" + routingKey);
    }
}
