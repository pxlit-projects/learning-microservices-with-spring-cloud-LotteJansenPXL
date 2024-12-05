package be.pxl.services.service;

import be.pxl.services.domain.LogInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogBookService implements ILogBookService {

    @RabbitListener(queues = "messageQueue")
    @Override
    public void sendMessage(LogInput logInput) {
        log.info("receiving log... ");
        log.info("Sending... {}", logInput.getMessage());
        log.info("To: {}", logInput.getSender());
        log.info("At: {}", logInput.getTimestamp());
    }
}
