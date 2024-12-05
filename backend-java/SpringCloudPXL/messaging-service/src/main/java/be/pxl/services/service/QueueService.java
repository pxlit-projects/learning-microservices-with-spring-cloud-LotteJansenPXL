package be.pxl.services.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    @RabbitListener(queues = "messageQueue") // Luistert naar de queue 'myQueue'
    public void listen(String in) {
        System.out.println("Message read from messageQueue : " + in);
    }
}

