package be.pxl.services.client;

import be.pxl.services.domain.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "logbook-service", url = "${notification.service.url}") //handmatige URL wegens fout in computernaam
public interface NotificationClient {

    @PostMapping("/api/notification" )
    void sendNotification(@RequestBody NotificationRequest notificationRequest);
}
