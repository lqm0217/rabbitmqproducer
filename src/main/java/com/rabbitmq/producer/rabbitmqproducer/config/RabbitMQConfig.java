package com.rabbitmq.producer.rabbitmqproducer.config;

import com.rabbitmq.producer.rabbitmqproducer.common.ProducerReturnCallBack;
import com.rabbitmq.producer.rabbitmqproducer.common.ProducerConfirmCallBack;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private ProducerReturnCallBack producerReturnCallBack;


    @Autowired
    private ProducerConfirmCallBack producerConfirmCallBack;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setConfirmCallback(producerConfirmCallBack);
        // 实现消息 [rabbitTemplate.send(message); ]时设置ReturnCallback
        // rabbitTemplate.setReturnCallback(sendReturnCallBack);
        return rabbitTemplate;
    }

}
