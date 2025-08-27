package be.pxl.services;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class QueueTests {

    @Autowired
    private QueueService queueService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue myQueue;

    @Test
    void testSendMessageUsesRabbitTemplate() {
        String msg = "Hello Rabbit!";

        queueService.sendMessage(msg);

        verify(rabbitTemplate).convertAndSend("myQueue", msg);
    }
}
