package be.pxl.services.service;

import be.pxl.services.domain.Notification;
import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationListener {
    private final NotificationRepository notificationRepository;

    public NotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues="myQueue")
    public void receiveMessage(NotificationRequest request) {
        Notification notification = Notification.builder()
                        .sender(request.getSender())
                        .receiver(request.getReceiver())
                        .subject(request.getSubject())
                        .message(request.getMessage())
                        .timestamp(request.getTimestamp())
                .build();
        notificationRepository.save(notification);
    }

}
