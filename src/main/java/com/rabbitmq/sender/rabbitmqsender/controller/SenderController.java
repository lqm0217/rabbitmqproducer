package com.rabbitmq.sender.rabbitmqsender.controller;


import com.rabbitmq.sender.rabbitmqsender.domain.Project;
import com.rabbitmq.sender.rabbitmqsender.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/sender")
public class SenderController {

    @Autowired
    private SenderService senderService;

    @PostMapping("/fanout")
    public String fanout(Project project) {

        senderService.sendFanout(project);
        return "fanout";
    }

    @PostMapping("/direct")
    public String direct(Project project) {

        senderService.sendDirect(project);
        return "direct";
    }

    @PostMapping("/topic")
    public String topic(Project project) {

        senderService.sendTopic(project);
        return "topic";
    }
}
