package be.pxl.services.service;

import be.pxl.services.domain.Notification;
import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.domain.dto.NotificationResponse;
import be.pxl.services.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final NotificationRepository notificationRepository;
    @Override
    public List<NotificationResponse> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream().map(notification -> mapToProductResponse(notification)).toList();
    }

    @Override
    public void createNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .subject(notificationRequest.getSubject())
                .message(notificationRequest.getMessage())
                .sender(notificationRequest.getSender())
                .receiver(notificationRequest.getReceiver())
                .build();
        notificationRepository.save(notification);
    }

    private NotificationResponse mapToProductResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .sender(notification.getSender())
                .receiver(notification.getReceiver())
                .build();
    }
}
