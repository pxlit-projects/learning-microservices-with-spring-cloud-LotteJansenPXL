package be.pxl.services.service;

import be.pxl.services.domain.LogInput;

public interface ILogBookService {
    void sendMessage(LogInput logInput);
}
