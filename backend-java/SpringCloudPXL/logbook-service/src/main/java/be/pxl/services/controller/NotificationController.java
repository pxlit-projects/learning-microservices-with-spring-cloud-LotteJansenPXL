package be.pxl.services.controller;

import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationService;

    @GetMapping
    public ResponseEntity getAllNotifications() {
        return new ResponseEntity(notificationService.getAllNotifications(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendNotification(@RequestBody NotificationRequest notification) {
        notificationService.createNotification(notification);
    }
}
