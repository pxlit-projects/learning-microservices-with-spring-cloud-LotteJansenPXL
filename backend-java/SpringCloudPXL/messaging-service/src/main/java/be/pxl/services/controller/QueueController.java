package be.pxl.services.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String sendMessage() {
        String message = "Hello, world!";
        rabbitTemplate.convertAndSend("messageQueue", message); // Verstuur bericht naar 'myQueue'
        return "Message Sent: " + message;
    }
}
