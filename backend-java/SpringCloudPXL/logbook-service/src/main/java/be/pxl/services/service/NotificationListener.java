package be.pxl.services.service;

import be.pxl.services.domain.Notification;
import be.pxl.services.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {
    private final NotificationRepository notificationRepository;

    public NotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues="myQueue")
    public void receiveMessage(String message) {
        Notification notification = Notification.builder()
                        .message(message).build();
        notificationRepository.save(notification);
    }

}
