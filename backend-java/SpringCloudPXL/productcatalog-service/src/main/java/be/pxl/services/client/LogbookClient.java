package be.pxl.services.client;

import be.pxl.services.domain.dto.LogbookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "logbook-service") // -> naam van de service
public interface LogbookClient {

    @PostMapping("/logbook/")
    void sendNotification(@RequestBody LogbookRequest logbookRequest);
}

