package be.pxl.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "myQueue")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }

//    public void sendMessage(String message) {
//        rabbitTemplate.convertAndSend("myQueue", message);
//    }

}
