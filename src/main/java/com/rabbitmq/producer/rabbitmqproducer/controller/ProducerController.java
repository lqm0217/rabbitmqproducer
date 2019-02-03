package com.rabbitmq.producer.rabbitmqproducer.controller;


import com.rabbitmq.producer.rabbitmqproducer.domain.Project;
import com.rabbitmq.producer.rabbitmqproducer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/sender")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping("/fanout")
    public String fanout(Project project) throws Exception {

        producerService.sendFanout(project);
        return "fanout";
    }

    @PostMapping("/direct")
    public String direct(Project project) throws Exception {

        producerService.sendDirect(project);
        return "direct";
    }

    @PostMapping("/topic")
    public String topic(Project project) throws Exception {

        producerService.sendTopic(project);
        return "topic";
    }
}
