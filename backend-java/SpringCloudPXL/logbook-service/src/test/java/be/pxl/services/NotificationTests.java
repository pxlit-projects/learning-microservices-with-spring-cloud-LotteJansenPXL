package be.pxl.services;


import be.pxl.services.domain.Notification;
import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.repository.NotificationRepository;
import be.pxl.services.service.NotificationListener;
import be.pxl.services.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class NotificationTests {

    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    private NotificationListener notificationListener;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(notificationRepository);
        notificationListener = new NotificationListener(notificationRepository);
    }

    @Test
    void testCreateNotificationDirectly() {
        NotificationRequest request = new NotificationRequest();
        request.setSender("Alice");
        request.setReceiver("Bob");
        request.setSubject("Hello");
        request.setMessage("Test message");

        notificationService.createNotification(request);

        // verify the repository save was called
        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(1)).save(captor.capture());

        Notification saved = captor.getValue();
        assertThat(saved.getSender()).isEqualTo("Alice");
        assertThat(saved.getReceiver()).isEqualTo("Bob");
        assertThat(saved.getSubject()).isEqualTo("Hello");
        assertThat(saved.getMessage()).isEqualTo("Test message");
    }

    @Test
    void testReceiveNotificationFromRabbitMQMock() {
        NotificationRequest request = new NotificationRequest();
        request.setSender("Carol");
        request.setReceiver("Dave");
        request.setSubject("Hi");
        request.setMessage("Mocked message");
        request.setTimestamp("2025-08-27T12:00:00");

        // simulate RabbitMQ delivering the message
        notificationListener.receiveMessage(request);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(1)).save(captor.capture());

        Notification saved = captor.getValue();
        assertThat(saved.getSender()).isEqualTo("Carol");
        assertThat(saved.getReceiver()).isEqualTo("Dave");
        assertThat(saved.getSubject()).isEqualTo("Hi");
        assertThat(saved.getMessage()).isEqualTo("Mocked message");
        assertThat(saved.getTimestamp()).isEqualTo("2025-08-27T12:00:00");
    }

    @Test
    void testGetAllNotifications() {
        // Mock repository returning some notifications
        List<Notification> mockNotifications = new ArrayList<>();
        mockNotifications.add(Notification.builder().id(1L).sender("A").receiver("B").subject("S").message("M").timestamp("T").build());

        when(notificationRepository.findAll()).thenReturn(mockNotifications);

        var all = notificationService.getAllNotifications();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getSender()).isEqualTo("A");
    }
}
