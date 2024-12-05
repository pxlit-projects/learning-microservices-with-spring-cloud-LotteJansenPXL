package be.pxl.services.domain;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    @Bean
    public Queue messageQueue() {
        return new Queue("messageQueue", false);
    }
}
