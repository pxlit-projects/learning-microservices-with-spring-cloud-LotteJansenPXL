package be.pxl.services.controller;

import be.pxl.services.domain.LogInput;
import be.pxl.services.service.ILogBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/logbook")
public class LogBookController {

    private final ILogBookService logBookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@RequestBody LogInput logInput) {
        logBookService.sendMessage(logInput);
    }
}

